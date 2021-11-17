package com.tove.market.job.tick;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
public class ThreadPoolProducer {
    private static final int QUEUE_CAPACITY = 100;
    private static final Long KEEP_ALIVE_TIME = 1L;


    public static ThreadPoolExecutor produceMainTheadPool(){
        ThreadPoolExecutor mainThreadPool = new ThreadPoolExecutor(
                8,
                8,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy());
        return mainThreadPool;
    }

    public static ThreadPoolExecutor produceCusumerTheadPool(){
        ThreadPoolExecutor mainThreadPool = new ThreadPoolExecutor(
                10,
                10,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy());
        return mainThreadPool;
    }
}
