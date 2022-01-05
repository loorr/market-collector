package com.tove.market.tushare.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tove.market.tushare.common.DateUtil;
import com.tove.market.tushare.dao.TradeCalMapper;
import com.tove.market.tushare.model.ExchangeEnum;
import com.tove.market.tushare.model.TradeCal;
import com.tove.market.tushare.service.TradeCalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.tove.market.tushare.common.DateUtil.PATTERN_YYYY_MM_DD;

@Service
public class TradeCalServiceImpl  extends ServiceImpl<TradeCalMapper, TradeCal> implements TradeCalService {

    @Resource
    private TradeCalMapper tradeCalMapper;

    public int getOpenDaysCount(String start, String end){
        return tradeCalMapper.getOpenDaysCount(start, end);
    }


    @Override
    public List<String> getStartDateList(Date startDate){
        List<String> startDateList = new ArrayList<>();
        startDateList.add(DateUtil.formatDate(startDate, PATTERN_YYYY_MM_DD));

        while (true){
            TradeCal tradeCal = tradeCalMapper.getTradeCal(startDate);
            if (tradeCal == null){
                break;
            }
            startDateList.add(DateUtil.formatDate(tradeCal.getCalDate(), PATTERN_YYYY_MM_DD));
            startDate = tradeCal.getCalDate();
        }
        return startDateList;
    }

    // TODO
    @Override
    public Date getDiffDate(ExchangeEnum exchangeEnum, int n) {
        return null;
    }
}
