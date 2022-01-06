package com.tove.market.tushare.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.tove.market.tushare.model.DayData;

public interface DayDataService extends IService<DayData> {

    void synAllDaliyData();

    boolean checkLastStockDate(String symbol);

    void synDailyDataByDate(String startDate, String endDate);
}
