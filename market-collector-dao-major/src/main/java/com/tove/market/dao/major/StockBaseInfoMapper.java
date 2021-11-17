package com.tove.market.dao.major;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StockBaseInfoMapper {
    @Select("select * from `stock_base_info`")
    List<StockBaseInfo> getAllStockInfo();

    @Insert("<script>"  +
            "INSERT INTO stock_base_info " +
            "(code, name) VALUES " +
            " <foreach collection ='stockBaseInfoList' item='item'  separator =',' > " +
            " (#{item.code}, #{item.name} )" +
            " </foreach> "+
            "</script>")
    int batchInsert(@Param("stockBaseInfoList") List<StockBaseInfo> stockBaseInfoList);
}
