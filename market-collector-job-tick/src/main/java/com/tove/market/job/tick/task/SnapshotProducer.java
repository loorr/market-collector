package com.tove.market.job.tick.task;

import com.tove.market.job.tick.model.StockSnapshot;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static jodd.util.ThreadUtil.sleep;

@Log4j2
public class SnapshotProducer implements Runnable{

    private final List<TaskExecutor> taskExecutors;
    private final BlockingQueue<StockSnapshot> queue;
    private final AtomicInteger runCount = new AtomicInteger(0);

    public SnapshotProducer(BlockingQueue<StockSnapshot> queue, List<TaskExecutor> taskExecutors){
        this.queue = queue;
        this.taskExecutors = taskExecutors;
    }

    @Override
    public void run() {
        System.out.println("runCount before : {} " + runCount.get() + " " + Thread.currentThread().getName());
        if (runCount.get() > 1){
            return;
        }
        if (runCount.get() == 0){
            runCount.incrementAndGet();
        }
        System.out.println("runCount: {} " + runCount.get() + " " + Thread.currentThread().getName());
        while (true){
            long startTime = System.currentTimeMillis();
            List<Long> timer = new ArrayList<>();
            for (TaskExecutor taskExecutor: taskExecutors){
                long startTime1 = System.currentTimeMillis();
                List<StockSnapshot> stockSnapshots = taskExecutor.getStockSnapshot();
                if (stockSnapshots == null){
                    continue;
                }
                for (StockSnapshot stockSnapshot : stockSnapshots) {
                    queue.offer(stockSnapshot);
                }
                long endTime2 = System.currentTimeMillis();
                timer.add(endTime2 - startTime1);
            }
            long endTime = System.currentTimeMillis();
            // TODO 测试
             System.out.println(Thread.currentThread().getName() + " : " + "time: " + (endTime-startTime)
                    + " size: " + queue.size() + " " + timer);
            sleep(1000*3);
        }
    }
}
