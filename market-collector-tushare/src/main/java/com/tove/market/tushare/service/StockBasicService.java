package com.tove.market.tushare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tove.market.tushare.model.StockBasic;

import java.util.List;

public interface StockBasicService extends IService<StockBasic> {
    List<StockBasic> getAllListStockInfo();
}
