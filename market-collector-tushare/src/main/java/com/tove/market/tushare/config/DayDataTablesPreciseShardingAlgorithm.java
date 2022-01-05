package com.tove.market.tushare.config;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

public class DayDataTablesPreciseShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {
        // 分片字段值
        String value = preciseShardingValue.getValue();
        // 现在算法是:%2 求余如果是0则xmjbq_user0,如果是1则xmjbq_user1。但是由于id是字符串而且是很长的，所以截取最后一位然后转为Integer类型再求余

        Integer number = Integer.valueOf(value);
        int result = number % 5;
        for (String s : collection) {
            if(s.endsWith(result+"")){
                return s;
            }
        }
        throw new UnsupportedOperationException();
    }
}
