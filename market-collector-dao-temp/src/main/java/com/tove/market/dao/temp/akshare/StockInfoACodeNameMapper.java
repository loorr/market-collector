package com.tove.market.dao.temp.akshare;

import com.tove.market.dao.temp.model.StockInfoACodeName;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StockInfoACodeNameMapper {
    @Select("select * from `akshare_stock_info_a_code_name`")
    List<StockInfoACodeName> getAllRecords();
}
