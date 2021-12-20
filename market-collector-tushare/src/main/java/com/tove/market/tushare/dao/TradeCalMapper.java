package com.tove.market.tushare.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tove.market.tushare.model.TradeCal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author user
 */
@Mapper
public interface TradeCalMapper extends BaseMapper<TradeCal> {

    @Select("select count(id) from trade_cal where cal_date >= #{start} " +
            "and cal_date  < #{end} and is_open = 1 and exchange = ‘SSE’")
    int getOpenDaysCount(@Param("start")String start, @Param("end") String end);

}
