package com.tove.market.tushare.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tove.market.tushare.dao.StockBasicMapper;
import com.tove.market.tushare.model.StockBasic;
import com.tove.market.tushare.request.TushareApi;
import com.tove.market.tushare.request.TushareResult;
import com.tove.market.tushare.service.StockBasicService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.tove.market.tushare.request.TushareUtil.converTushareResult;

@Service
public class StockBasicServiceImpl extends ServiceImpl<StockBasicMapper, StockBasic> implements StockBasicService {

    private TushareApi tushareApi = new TushareApi();

    public void synAllDaliyData() {
        TushareResult result = tushareApi.getStockBasic();
        if (result == null){
            return;
        }
        List<HashMap<String, Object>> ans = converTushareResult(result);
        List<StockBasic> stockBasicList = ans.stream()
                .map(o-> JSON.parseObject(JSON.toJSONString(o), StockBasic.class))
                .collect(Collectors.toList());
        this.saveBatch(stockBasicList);
    }
}
