package com.tove.market.job.tick;

import lombok.Getter;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

@Getter
public class TimeMonitor {
    // mmnitor
    /** 单位时间下载的公司数量 */
    private final AtomicLong perDownLoadRate = new AtomicLong(0);

    private Integer totalProducerNum = 0;
    private final AtomicLong producerSize = new AtomicLong(0);
    private final Set<String> producerSet = new CopyOnWriteArraySet<>();

    private final AtomicLong producerSizePeer4 = new AtomicLong(0);
    private final Set<String> producerSetPeer4 = new CopyOnWriteArraySet<>();

    /** 单位时间消费者存的数量 */
    private final AtomicLong perConsumerRate = new AtomicLong(0);

    public void setTotalProducerNum(Integer num){
        this.totalProducerNum = num;
    }

    public void addRunndProducer(String producerName){
        producerSize.incrementAndGet();
        producerSet.add(producerName);
    }

    public void initRunndProducer(){
        producerSizePeer4.set(producerSizePeer4.get() + producerSize.get());
        producerSetPeer4.addAll(producerSet);
        producerSize.set(0);
        producerSet.clear();
    }

    public void initRunndProducerPeer4(){
        producerSizePeer4.set(0);
        producerSetPeer4.clear();
    }

    public TimeMonitor(){
        ScheduledExecutorService scheduledThreadPool = ThreadPoolProducer.produceScheduleTheadPool();
        // 固定周期执行任务（与任务执行时间无关，周期是固定的）
        final ScheduledFuture<?> scheduledFuture = scheduledThreadPool.scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(new Date().getSeconds() + " scheduledFuture "
                                + totalProducerNum + " "+ producerSize.get()
                                + " " + producerSet.size()
                                + " " + producerSet.toString()
                        );
                        initRunndProducer();
                    }
                },
                2, 2, TimeUnit.SECONDS);
        // 固定周期执行任务（与任务执行时间无关，周期是固定的）
        final ScheduledFuture<?> scheduledFuturePeer4 = scheduledThreadPool.scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(new Date().getSeconds() + " scheduledFuture-peer4 "
                                + totalProducerNum + " "+ producerSize.get()
                                + " " + producerSetPeer4.size()
                                + " " + producerSetPeer4.toString()
                        );
                        initRunndProducer();
                    }
                },
                4, 4, TimeUnit.SECONDS);
    }
}
