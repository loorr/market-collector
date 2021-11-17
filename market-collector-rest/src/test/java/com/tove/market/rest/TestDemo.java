package com.tove.market.rest;

import com.tove.market.dao.major.StockBaseInfoMapper;
import com.tove.market.dao.temp.akshare.StockInfoACodeNameMapper;
import com.tove.market.dao.temp.model.StockInfoACodeName;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@SpringBootTest
public class TestDemo {

    @Resource
    private StockBaseInfoMapper stockBaseInfoMapper;



    @Test
    void testDemo(){
        stockBaseInfoMapper.getAllStockInfo();
    }

}
