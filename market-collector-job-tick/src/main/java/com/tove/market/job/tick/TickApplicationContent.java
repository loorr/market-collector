package com.tove.market.job.tick;

import com.tove.market.job.tick.service.RedisService;
import lombok.Getter;
import org.redisson.api.RedissonClient;

import java.util.concurrent.ThreadPoolExecutor;

import static com.tove.market.job.tick.TickApplicationConfig.REDIS_URL;

@Getter
public final class TickApplicationContent {
    private final RedissonClient redissonClient;
    private final ThreadPoolExecutor mainThreadPool;
    private final RedisService redisService;

    public TickApplicationContent(){
        this.redissonClient = new RedissonDataStore(REDIS_URL).getRedissonClient();
        this.mainThreadPool = ThreadPoolProducer.produceMainTheadPool();
        this.redisService = new RedisService(redissonClient);
    }
}
