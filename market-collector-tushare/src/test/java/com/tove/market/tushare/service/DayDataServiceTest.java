package com.tove.market.tushare.service;

import com.tove.market.tushare.service.impl.DayDataServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DayDataServiceTest {
    @Resource
    private DayDataService dayDataService;

    @Resource
    private DayDataServiceImpl dayDataServiceImpl;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void synAllDaliyData() {
        dayDataService.synAllDaliyData();
    }

    @Test
    void deleteDataTest(){
        dayDataServiceImpl.deleteBySymbol("000014");
    }
}