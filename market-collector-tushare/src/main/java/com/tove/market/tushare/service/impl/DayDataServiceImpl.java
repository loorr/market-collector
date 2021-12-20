package com.tove.market.tushare.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.tove.market.tushare.dao.StockBasicMapper;
import com.tove.market.tushare.model.StockBasic;
import com.tove.market.tushare.request.TushareApi;
import com.tove.market.tushare.request.TushareResult;
import com.tove.market.tushare.service.DayDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.tove.market.tushare.request.TushareUtil.converTushareResult;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.collect;

@Service
public class DayDataServiceImpl extends ServiceImpl<StockBasicMapper,StockBasic> implements DayDataService{

    private TushareApi tushareApi = new TushareApi();

    @Override
    public void synAllDaliyData() {
        TushareResult result = tushareApi.getStockBasic();
        if (result == null){
            return;
        }
        List<HashMap<String, Object>> ans = converTushareResult(result);
        List<StockBasic> stockBasicList = ans.stream()
                .map(o->JSON.parseObject(JSON.toJSONString(o), StockBasic.class))
                .collect(Collectors.toList());
        this.saveBatch(stockBasicList);
    }
}
