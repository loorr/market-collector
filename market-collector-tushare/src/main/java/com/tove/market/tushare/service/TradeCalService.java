package com.tove.market.tushare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tove.market.tushare.model.ExchangeEnum;
import com.tove.market.tushare.model.TradeCal;

import java.util.Date;
import java.util.List;

public interface TradeCalService extends IService<TradeCal> {
    List<String> getStartDateList(Date startDate);

    Date getDiffDate(ExchangeEnum exchangeEnum, int n);
}
