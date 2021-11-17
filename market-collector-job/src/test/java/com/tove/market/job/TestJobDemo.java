package com.tove.market.job;

import com.tove.market.dao.major.StockBaseInfo;
import com.tove.market.dao.major.StockBaseInfoMapper;
import com.tove.market.dao.temp.akshare.StockInfoACodeNameMapper;
import com.tove.market.dao.temp.model.StockInfoACodeName;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.tove.market.common.Constant.SH_COMPANY_KEY;
import static com.tove.market.common.Constant.SZ_COMPANY_KEY;

@Slf4j
@SpringBootTest
public class TestJobDemo {

    @Resource
    private StockBaseInfoMapper stockBaseInfoMapper;

    @Resource
    private StockInfoACodeNameMapper stockInfoACodeNameMapper;

    @Resource
    private RedissonClient collectorRedissonClient;

    @Test
    void testDemo(){
        stockBaseInfoMapper.getAllStockInfo();
        List<StockInfoACodeName> result = stockInfoACodeNameMapper.getAllRecords();
        List<StockBaseInfo> stockBaseInfos = result.stream().map(item->{
            StockBaseInfo stockBaseInfo = new StockBaseInfo();
            BeanUtils.copyProperties(item, stockBaseInfo);
            return stockBaseInfo;
        }).collect(Collectors.toList());
        stockBaseInfoMapper.batchInsert(stockBaseInfos);
        log.info(result.toString());
    }

    @Test
    void setRedis(){
        List<StockBaseInfo> result = stockBaseInfoMapper.getAllStockInfo();
        if (CollectionUtils.isEmpty(result)){
            log.error("result is empty");
            return;
        }
        RSet<String> shSet = collectorRedissonClient.getSet(SH_COMPANY_KEY);
        RSet<String> szSet = collectorRedissonClient.getSet(SZ_COMPANY_KEY);

        List<String> shCodeList = result.stream()
                .filter(o->o.getCode().charAt(0) == '6')
                .map(StockBaseInfo::getCode)
                .collect(Collectors.toList());
        List<String> szCodeList = result.stream()
                .filter(o-> o.getCode().charAt(0) == '0' || o.getCode().charAt(0) == '3')
                .map(StockBaseInfo::getCode)
                .collect(Collectors.toList());
        szSet.addAll(szCodeList);
        shSet.addAll(shCodeList);
    }
}
