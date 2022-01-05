package com.tove.market.tushare.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.util.concurrent.RateLimiter;
import com.tove.market.tushare.dao.DayDataMapper;
import com.tove.market.tushare.model.DayData;
import com.tove.market.tushare.model.ExchangeEnum;
import com.tove.market.tushare.model.StockBasic;
import com.tove.market.tushare.request.TushareApi;
import com.tove.market.tushare.request.TushareResult;
import com.tove.market.tushare.request.TushareUtil;
import com.tove.market.tushare.service.DayDataService;
import com.tove.market.tushare.service.StockBasicService;
import com.tove.market.tushare.service.TradeCalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class DayDataServiceImpl extends ServiceImpl<DayDataMapper,DayData> implements DayDataService{

    private TushareApi tushareApi = new TushareApi();
    private static final int DATA_UPDATE_TIME = 17;

    @Resource
    private TradeCalService tradeCalService;

    @Resource
    private StockBasicService stockBasicService;

    @Resource
    private DayDataMapper dayDataMapper;

    private boolean checkLastStockDate(String symbol){
        Date lastDate = dayDataMapper.getLastTradeDate(symbol);
        return lastDate == null ? false : true;
    }

    private void getSingleSymbolAllData(StockBasic item){
        String tsCode = ExchangeEnum.getTushareCode(item.getSymbol());
        List<String> startDateList = tradeCalService.getStartDateList(item.getListDate());

        List<TushareResult> tushareResultList = new ArrayList<>();
        for (int i = 0; i < startDateList.size(); i++) {
            String startDate = startDateList.get(i);
            String endDate = null;
            if (i < startDateList.size() - 1){
                endDate = startDateList.get(i+1);
            }
            TushareResult tushareResult = tushareApi.getDailyData(tsCode, startDate, endDate);
            if (tushareResult == null){
                continue;
            }
            tushareResultList.add(tushareResult);
        }
        List<HashMap<String,Object>> result = TushareUtil.converTushareResult(tushareResultList);

        List<DayData> dayDataList = result.stream()
                .map(o->{
                    DayData dayData = JSON.parseObject(JSON.toJSONString(o), DayData.class);
                    dayData.setSymbol(item.getSymbol());
                    return dayData;
                })
                .collect(Collectors.toList());

        saveBatch(dayDataList);

        System.out.println("done: " + item.getSymbol());
    }

    @Override
    public void synAllDaliyData() {
        RateLimiter rateLimiter = RateLimiter.create(499/60);
        LambdaQueryWrapper<StockBasic> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StockBasic::getListStatus,"L");
        List<StockBasic> stockBasicList = stockBasicService.list(wrapper);
        for (StockBasic item: stockBasicList){
            if (rateLimiter.tryAcquire(1, 10, TimeUnit.SECONDS)) {
                if (checkLastStockDate(item.getSymbol())){
                    getSingleSymbolAllData(item);
                }
            }
        }
    }
}
