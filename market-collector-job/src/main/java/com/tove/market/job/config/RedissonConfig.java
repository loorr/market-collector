package com.tove.market.job.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RedissonConfig {

    @Value("${spring.redis.url}")
    private String redisUrl;

    @Bean(name = "collectorRedissonClient", destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
            .setAddress("redis://" + redisUrl)
            .setDatabase(0)
        ;
        config.setCodec(new StringCodec());
        return Redisson.create(config);
    }
}