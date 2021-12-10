package com.tove.market.job.tick;

import lombok.Getter;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.ByteArrayCodec;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;


@Getter
public class RedissonDataStore {
    private final RedissonClient redissonClient;
    private final RedissonClient redissonClientOfByte;

    public RedissonDataStore(String redisUrl){
        Config config = new Config();
        config.useSingleServer()
                //.setDatabase(1)
                .setAddress("redis://" + redisUrl);
        config.setCodec(new StringCodec());
        config.setThreads(4);
        config.setNettyThreads(4);

        redissonClient = Redisson.create(config);

        Config config2 = new Config();
        config.useSingleServer()
                //.setDatabase(1)
                .setAddress("redis://" + redisUrl);
        config.setCodec(new ByteArrayCodec());
        config.setThreads(4);
        config.setNettyThreads(4);

        redissonClientOfByte = Redisson.create(config);
    }




}
