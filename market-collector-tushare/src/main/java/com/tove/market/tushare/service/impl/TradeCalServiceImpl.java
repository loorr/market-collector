package com.tove.market.tushare.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tove.market.tushare.dao.TradeCalMapper;
import com.tove.market.tushare.model.TradeCal;
import com.tove.market.tushare.service.TradeCalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TradeCalServiceImpl  extends ServiceImpl<TradeCalMapper, TradeCal> implements TradeCalService {

    @Resource
    private TradeCalMapper tradeCalMapper;

    public int getOpenDaysCount(String start, String end){
        return tradeCalMapper.getOpenDaysCount(start, end);
    }

}
