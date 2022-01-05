package com.tove.market.tushare.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tove.market.tushare.model.DayData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * @author user
 */
@Mapper
public interface DayDataMapper extends BaseMapper<DayData> {


    @Insert("insert into day_data_3" +
            " (symbol, trade_date, `open`, high, low, `close`, pre_close, `change`, pct_chg, vol, amount)" +
            " values(#{symbol},#{tradeDate},#{open},#{high},#{low},#{close}," +
            "#{preClose},#{change},#{pctChg},#{vol},#{amount})")
    int insertMy(DayData dayData);


    @Select("select max(trade_date) from day_data_0 where symbol = #{symbol}")
    Date getLastTradeDate(String symbol);
}
