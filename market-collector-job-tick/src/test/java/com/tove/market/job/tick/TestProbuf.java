package com.tove.market.job.tick;

import com.alibaba.fastjson.JSON;
import com.google.protobuf.InvalidProtocolBufferException;
import com.tove.market.job.tick.model.SnapshotProto;
import com.tove.market.job.tick.model.StockSnapshot;
import com.tove.market.job.tick.model.TickSnapshot;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;

import java.util.List;
import java.util.stream.Collectors;

import static com.tove.market.job.tick.TickApplicationConfig.REDIS_URL;

@DisplayName("Probuf Test")
public class TestProbuf {

    public static RedissonClient redissonClient;
    private static RedissonClient redissonClientOfByte;
    @BeforeAll
    public static void init() {
        System.out.println("初始化数据");
        RedissonDataStore redissonDataStore = new RedissonDataStore(REDIS_URL);
        redissonClient = redissonDataStore.getRedissonClient();
        redissonClientOfByte = redissonDataStore.getRedissonClientOfByte();
    }

    @AfterAll
    public static void cleanup() {
        System.out.println("清理数据");
        redissonClient.shutdown();
    }


    @Test
    void test(){
        String tickKey = "tick:2021/11/23:0600007";
        RQueue<String> rQueue = redissonClient.getQueue(tickKey);
        List<String> stockSnapshotList = rQueue.readAll();
        List<TickSnapshot> sspList = stockSnapshotList.stream().map(o->
            JSON.parseObject(o, TickSnapshot.class)
        ).collect(Collectors.toList());
        TickSnapshot tickSnapshot = sspList.get(0);
        StockSnapshot snapshot = TickSnapshot.coverTickToStock(tickSnapshot);
        SnapshotProto.Tick sspTick = StockSnapshot.covertSnapshotProtoTick(snapshot);
        RBucket<String> sBucket = redissonClient.getBucket("test:StockSnapshot");
        sBucket.set(snapshot.toString());
        RBucket<String> stBucket = redissonClient.getBucket("test:TickSnapshot");
        stBucket.set(tickSnapshot.toString());

        RBucket<byte[]> rBucket = redissonClientOfByte.getBucket("test:Probuf");
        rBucket.set(sspTick.toByteArray());
        byte[] r = rBucket.get();
        try {
            SnapshotProto.Tick sspTick2 = SnapshotProto.Tick.parseFrom(r);
            System.out.println("ss");
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        System.out.println("ss");
    }
}
