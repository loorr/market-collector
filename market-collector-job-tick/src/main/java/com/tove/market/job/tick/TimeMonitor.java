package com.tove.market.job.tick;

import lombok.Getter;

import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Getter
public class TimeMonitor {
    // mmnitor
    /** 单位时间下载的公司数量 */
    private final AtomicLong perDownLoadRate = new AtomicLong(0);
    /** 单位时间消费者存的数量 */
    private final AtomicLong perConsumerRate = new AtomicLong(0);

    public TimeMonitor(){
        ScheduledExecutorService scheduledThreadPool = ThreadPoolProducer.produceScheduleTheadPool();
        // 固定周期执行任务（与任务执行时间无关，周期是固定的）
        final ScheduledFuture<?> scheduledFuture = scheduledThreadPool.scheduleAtFixedRate(
                () -> System.out.println("scheduledFuture " + new Date()),
1, 2, TimeUnit.SECONDS);
    }

}
