package com.tove.market.job.tick;

import com.tove.market.job.tick.service.RedisService;
import com.tove.market.job.tick.model.StockSnapshot;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.redisson.api.RDeque;
import org.redisson.api.RedissonClient;

import static com.tove.market.job.tick.TickApplicationConfig.REDIS_URL;

@DisplayName("Redisson Test")
public class RedissonTest {

    public static RedissonClient redissonClient;

    @BeforeAll
    public static void init() {
        System.out.println("初始化数据");
        RedissonDataStore redissonDataStore = new RedissonDataStore(REDIS_URL);
        redissonClient = redissonDataStore.getRedissonClient();
    }

    @AfterAll
    public static void cleanup() {
        System.out.println("清理数据");
        redissonClient.shutdown();
    }

    @Test
    void  testDeque(){
        RedisService redisService = new RedisService(redissonClient);
        String key = "tick:2021/11/22:06000061";
        RDeque<StockSnapshot> rQueue = redissonClient.getDeque(key);
        // 测试最后一个数据
        System.out.println(rQueue.peekLast());
        System.out.println(rQueue.peekFirst());
    }

}
