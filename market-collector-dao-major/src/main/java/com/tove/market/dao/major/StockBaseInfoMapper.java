package com.tove.market.dao.major;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StockBaseInfoMapper {
    @Select("select * from `stock_base_info`")
    List<StockBaseInfo> getAllStockInfo();
}
