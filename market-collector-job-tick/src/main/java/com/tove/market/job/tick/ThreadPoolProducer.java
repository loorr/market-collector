package com.tove.market.job.tick;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.*;

@Log4j2
public class ThreadPoolProducer {
    private static final int QUEUE_CAPACITY = 100;
    private static final Long KEEP_ALIVE_TIME = 1L;


    public static ThreadPoolExecutor produceMainTheadPool(){
        return new ThreadPoolExecutor(
                16,
                16,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public static ThreadPoolExecutor produceCusumerTheadPool(){
        return new ThreadPoolExecutor(
                16,
                16,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }


    public static ScheduledExecutorService produceScheduleTheadPool(){
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);
        return scheduledThreadPool;
    }
}
