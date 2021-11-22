package com.tove.market.job.tick.task;

import com.tove.market.job.tick.model.StockSnapshot;
import com.tove.market.job.tick.service.RedisService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SnapshotConsumer implements Runnable{
    public volatile static ConcurrentHashMap<String, StockSnapshot> lastTickMap = new ConcurrentHashMap(5000);
    public volatile static  ConcurrentHashMap<String, List<StockSnapshot>> tempSnapshot = new ConcurrentHashMap<>(5000);
    public final static Integer batchSize = 1;
    private volatile boolean isRunning = true;
    private final BlockingQueue<StockSnapshot> queue;
    private final RedisService redisService;

    public SnapshotConsumer(BlockingQueue<StockSnapshot> queue, RedisService redisService){
        this.queue = queue;
        this.redisService = redisService;
    }

    @Override
    public void run() {
        System.out.println("SnapshotConsumer 启动");
        try {
            while (isRunning) {
                StockSnapshot item = queue.take();

                boolean sign = canStore(item);
                if (!sign){
                    continue;
                }
                lastTickMap.put(item.getCode(), item);

                String code = item.getCode();

                List<StockSnapshot> list = tempSnapshot.getOrDefault(code, Collections.synchronizedList(new ArrayList<>(batchSize)));
                list.add(item);
                tempSnapshot.put(code,list);


                if (list.size() >= batchSize){

                    tempSnapshot.put(code,  Collections.synchronizedList(new ArrayList<>(batchSize)));
                    redisService.addStockSnapShot(list);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        isRunning = false;
    }

    private boolean canStore(StockSnapshot item){
        if (lastTickMap.containsKey(item.getCode())){
            StockSnapshot lastSsp = lastTickMap.get(item.getCode());
            System.out.println(item.getCode() + " " + item.getSymbol() + " " + lastSsp.getTime() + " " + item.getTime());
            if (lastSsp.getTime().equals(item.getTime())){
                return false;
            }
        }
        return true;
    }
}
