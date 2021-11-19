package com.tove.market.job.tick.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class SnapshotProducer implements Runnable{

    private final List<TaskExecutor> taskExecutors;
    private final BlockingQueue<StockSnapshot> queue;

    public SnapshotProducer(BlockingQueue<StockSnapshot> queue, List<TaskExecutor> taskExecutors){
        this.queue = queue;
        this.taskExecutors = taskExecutors;
    }

    @Override
    public void run() {
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
                timer.add(endTime2-startTime1);
            }
            long endTime = System.currentTimeMillis();
            //if (endTime-startTime>200){
                System.out.println(Thread.currentThread().getName() + " : " + "time: " + (endTime-startTime)
                        + " size: " + queue.size() + " " + timer);
            //}
        }
    }


}
