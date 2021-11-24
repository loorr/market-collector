package com.tove.market.job.tick.service;

import com.tove.market.job.tick.model.StockSnapshot;
import com.tove.market.job.tick.model.TickSnapshot;
import org.redisson.api.RQueue;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;

import java.util.*;
import java.util.stream.Collectors;

import static com.tove.market.common.Constant.*;

public class RedisService {
    private final RedissonClient redisClient;

    public RedisService(RedissonClient redisClient){
        this.redisClient = redisClient;
    }

    public List<String> getSHCompanysSymbol(){
        RSet<String> shSet = redisClient.getSet(SH_COMPANY_KEY);
        Set<String> rows = shSet.readAll();
        return new ArrayList<>(rows);
    }

    public List<String> getSZCompanysSymbol(){
        RSet<String> szSet = redisClient.getSet(SZ_COMPANY_KEY);
        return new ArrayList<>(szSet.readAll());
    }

    public void addStockSnapShot(StockSnapshot snapshot){
        if (snapshot == null){
            System.out.println("Snapshot is null");
            return;
        }
        String[] dateTime = snapshot.getTime().split("\\s+");
        String key = getKey(TICK_DATE_SYMBOL,  dateTime[0], snapshot.getCode());
        RQueue<StockSnapshot> rQueue = redisClient.getQueue(key);
        rQueue.add(snapshot);
    }

    public void addStockSnapShot(List<StockSnapshot> stockSnapshotList){
        if (stockSnapshotList == null || stockSnapshotList.isEmpty()){
            return;
        }
        StockSnapshot ssp = stockSnapshotList.get(0);
        String[] dateTime = ssp.getTime().split("\\s+");
        String key = getKey(TICK_DATE_SYMBOL,  dateTime[0], ssp.getCode());

        List<TickSnapshot> tickList = stockSnapshotList.stream().map(o->
           TickSnapshot.covertStockToTick(o)
        ).collect(Collectors.toList());

        RQueue<TickSnapshot> rQueue = redisClient.getQueue(key);
        rQueue.addAll(tickList);
    }


//    public void addStockSnapShotProBuff(List<StockSnapshot> stockSnapshotList){
//        if (stockSnapshotList == null || stockSnapshotList.isEmpty()){
//            return;
//        }
//        StockSnapshot ssp = stockSnapshotList.get(0);
//        String[] dateTime = ssp.getTime().split("\\s+");
//        String key = getKey(TICK_DATE_SYMBOL,  dateTime[0], ssp.getCode());
//
//        List<TickSnapshot> tickList = stockSnapshotList.stream().map(o->
//                TickSnapshot.covertStockToTick(o)
//        ).collect(Collectors.toList());
//
//        RQueue<TickSnapshot> rQueue = redisClient.getQueue(key);
//        rQueue.addAll(tickList);
//    }

    // TODO
    public List<StockSnapshot> getAllLastSnapshot(){
        //
        return null;
    }
}
