package com.tove.market.job.tick;

import lombok.Getter;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;


@Getter
public class RedissonDataStore {
    private final RedissonClient redissonClient;

    public RedissonDataStore(String redisUrl){
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + redisUrl);
        config.setCodec(new StringCodec());

        redissonClient = Redisson.create(config);
    }


}
