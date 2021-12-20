package com.tove.market.tushare.service.impl;

import com.tove.market.tushare.request.TushareApi;
import com.tove.market.tushare.service.DayDataService;
import org.springframework.stereotype.Service;

@Service
public class DayDataServiceImpl implements DayDataService {

    private TushareApi tushareApi = new TushareApi();


    @Override
    public void synAllDaliyData() {
        tushareApi.getStockBasic();
        System.out.printf("test");
    }
}
