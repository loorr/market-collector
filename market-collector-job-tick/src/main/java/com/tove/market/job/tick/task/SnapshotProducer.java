package com.tove.market.job.tick.task;

import com.tove.market.job.tick.TimeMonitor;
import com.tove.market.job.tick.model.StockSnapshot;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;


@Log4j2
public class SnapshotProducer implements Runnable{
    private final String producerName;
    private final List<TaskExecutor> taskExecutors;
    private final BlockingQueue<StockSnapshot> queue;
    private final TimeMonitor timeMonitor;
    private final AtomicInteger runCount = new AtomicInteger(0);
    private final AtomicLong lastTime = new AtomicLong( 1637510495169L);

    public String getName(){
        return producerName;
    }

    public SnapshotProducer(String producerName, TimeMonitor timeMonitor,
                            BlockingQueue<StockSnapshot> queue, List<TaskExecutor> taskExecutors){
        this.producerName = producerName;
        this.queue = queue;
        this.taskExecutors = taskExecutors;
        this.timeMonitor = timeMonitor;
    }

    public Boolean checkCanGet(){
        if (lastTime == null){
            lastTime.set(System.currentTimeMillis());
            return true;
        }
        long diff = System.currentTimeMillis() - lastTime.get();
        return  diff >= 1 * 1000;
    }

    @Override
    public void run() {
        //System.out.println("runCount before : {} " + runCount.get() + " " + Thread.currentThread().getName());
        if (runCount.get() > 1){
            return;
        }
        if (runCount.get() == 0){
            runCount.incrementAndGet();
        }
        //System.out.println(this.producerName + " runCount: {} " + runCount.get() + " " + Thread.currentThread().getName());

        lastTime.set(System.currentTimeMillis());

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


        runCount.decrementAndGet();
        if (queue.size()>0){
//            System.out.println(this.producerName + " "+Thread.currentThread().getName() + " : " + "time: " + (endTime-startTime)
//                    + " size: " + queue.size() + " " + timer);
            timeMonitor.addRunndProducer(this.producerName);
        }

    }
}
