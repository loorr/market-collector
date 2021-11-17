package com.tove.market.job.content;

import com.tove.market.dao.major.StockBaseInfo;
import com.tove.market.dao.major.StockBaseInfoMapper;
import com.tove.market.dao.temp.akshare.StockInfoACodeNameMapper;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.tove.market.common.Constant.SH_COMPANY_KEY;
import static com.tove.market.common.Constant.SZ_COMPANY_KEY;

@Log4j2
@Component
public class StockInfoHandler {

    @Resource
    private StockBaseInfoMapper stockBaseInfoMapper;

    @Resource
    private StockInfoACodeNameMapper stockInfoACodeNameMapper;

    @Resource
    private RedissonClient collectorRedissonClient;

    @XxlJob("demoJobHandlerkk")
    public void demoJobHandler() throws Exception {
        XxlJobHelper.log("XXL-JOB, Hello World.");

        for (int i = 0; i < 5; i++) {
            XxlJobHelper.log("beat at:" + i);
            System.out.printf("1212121 " + i);
            TimeUnit.SECONDS.sleep(2);
        }
        XxlJobHelper.handleSuccess();
    }

    private void addCompanySymbolToRedis(){
        XxlJobHelper.log("addCompanySymbolToRedis start");
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
        XxlJobHelper.handleSuccess();
    }
}
