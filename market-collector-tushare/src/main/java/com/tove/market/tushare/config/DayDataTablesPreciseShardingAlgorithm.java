package com.tove.market.tushare.config;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @author user
 */
public class DayDataTablesPreciseShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    @Override
    public String doSharding(Collection<String> tableNames, PreciseShardingValue<String> preciseShardingValue) {
        String value = preciseShardingValue.getValue();
         int result = Integer.valueOf(value) % 15;
        for (String s : tableNames) {
            if(s.endsWith(result+"")){
                return s;
            }
        }
        throw new UnsupportedOperationException();
    }
}
