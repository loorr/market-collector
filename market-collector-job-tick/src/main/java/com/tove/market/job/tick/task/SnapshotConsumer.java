package com.tove.market.job.tick.task;

import com.tove.market.job.tick.service.RedisService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SnapshotConsumer implements Runnable{
    public final static ConcurrentHashMap<String, StockSnapshot> lastTickMap = new ConcurrentHashMap(5000);
    public final static ConcurrentHashMap<String, List<StockSnapshot>> tempSnapshot = new ConcurrentHashMap<>();

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

                if (!canStore(item)){
                    continue;
                }

                String code = item.getCode();
                if (!tempSnapshot.containsKey(code)){
                    tempSnapshot.put(code,  Collections.synchronizedList(new ArrayList<>(10)));
                }
                List<StockSnapshot> list = tempSnapshot.get(code);
                list.add(item);
                lastTickMap.put(item.getCode(), item);
                if (list.size() == 10){
                    tempSnapshot.put(code,  Collections.synchronizedList(new ArrayList<>(10)));
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
            if (lastSsp.equals(item)){
                return false;
            }
        }
        return true;
    }
}
