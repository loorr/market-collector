package com.tove.market.tushare.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.util.concurrent.RateLimiter;
import com.tove.market.tushare.common.DateUtil;
import com.tove.market.tushare.dao.DayDataMapper;
import com.tove.market.tushare.model.DayData;
import com.tove.market.tushare.model.ExchangeEnum;
import com.tove.market.tushare.model.StockBasic;
import com.tove.market.tushare.model.SynTask;
import com.tove.market.tushare.request.TushareApi;
import com.tove.market.tushare.request.TushareResult;
import com.tove.market.tushare.request.TushareUtil;
import com.tove.market.tushare.service.DayDataService;
import com.tove.market.tushare.service.StockBasicService;
import com.tove.market.tushare.service.SynTaskService;
import com.tove.market.tushare.service.TradeCalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class DayDataServiceImpl extends ServiceImpl<DayDataMapper,DayData> implements DayDataService{

    private final TushareApi tushareApi = new TushareApi();
    private static final int DATA_UPDATE_TIME = 17;

    @Resource
    private TradeCalService tradeCalService;

    @Resource
    private StockBasicService stockBasicService;

    @Resource
    private DayDataMapper dayDataMapper;

    @Resource
    private SynTaskService synTaskService;

    @Override
    public boolean checkLastStockDate(String symbol){
        Date lastDate = dayDataMapper.getLastTradeDate(symbol);
        return lastDate == null ? false : true;
    }

    public void deleteBySymbol(String symbol){
        LambdaQueryWrapper<DayData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DayData::getSymbol, symbol);
        dayDataMapper.delete(wrapper);
    }

    private void getSingleSymbolAllData(StockBasic item) throws SocketTimeoutException {
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
    }

    @Override
    public void synAllDaliyData() {
        RateLimiter rateLimiter = RateLimiter.create(499/60);
        List<StockBasic> stockBasicList = stockBasicService.getAllListStockInfo();
        for (StockBasic item: stockBasicList){
            String taskName = SynTask.TaskNameEnum.SYMBOL_ALL_DAY_DATA.getFormatName(item.getSymbol());
            String taskType = SynTask.TaskTypeEnum.DAY_DATA_ALL.getTypeName();
            SynTask task = synTaskService.getOrCreateSynTaskByNameAndType(taskName, taskType);
            if (task.getState().equals(SynTask.TaskStateEnum.COMPLETED.getState())){
                continue;
            }else if (task.getState().equals(SynTask.TaskStateEnum.RUNNING.getState())){
                if (DateUtil.getBetweenSeconds(task.getStartTime(), new Date()) < 60*5){
                    continue;
                }else{
                    deleteBySymbol(item.getSymbol());
                }
            }else if (task.getState().equals(SynTask.TaskStateEnum.ERROR_PAUSED.getState())){
                deleteBySymbol(item.getSymbol());
            }

            task.setState(SynTask.TaskStateEnum.RUNNING.getState());
            synTaskService.saveOrUpdate(task);
            if (rateLimiter.tryAcquire(1, 10, TimeUnit.SECONDS)) {
                try{
                    getSingleSymbolAllData(item);
                    task.setState(SynTask.TaskStateEnum.COMPLETED.getState());
                    task.setEndTime(new Date());
                    synTaskService.saveOrUpdate(task);
                }catch (Exception e){
                    System.out.printf("ERROR " + e.getMessage());
                    task.setState(SynTask.TaskStateEnum.ERROR_PAUSED.getState());
                    task.setErrorLog(e.getMessage());
                    synTaskService.saveOrUpdate(task);
                    e.printStackTrace();
                }
            }
        }
    }
}
