package com.tove.market.job.tick.task;

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
            for (TaskExecutor taskExecutor: taskExecutors){
                List<StockSnapshot> stockSnapshots = taskExecutor.getStockSnapshot();
                for (int i = 0; i < stockSnapshots.size(); i++) {
                    queue.offer(stockSnapshots.get(i));
                }
            }
            long endTime = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + " : " + "time: " + (endTime-startTime) + " size: " + queue.size());
        }
    }
}
