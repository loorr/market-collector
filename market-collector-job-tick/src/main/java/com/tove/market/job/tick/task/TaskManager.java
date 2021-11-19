package com.tove.market.job.tick.task;
import com.tove.market.job.tick.ThreadPoolProducer;
import com.tove.market.job.tick.service.RedisService;
import lombok.SneakyThrows;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import static java.lang.Thread.sleep;

public class TaskManager implements Runnable {
    public static final Integer TASK_MAX_COMPANY_NUMBER = 50;
    private final Integer CONSUMER_NUMBER = 20;
    private final Integer PRODUCER_ITEM_NUMBER = 30;

    private final Set<String> symbolSet;
    private final List<TaskExecutor> taskExecutors;
    private final RedisService redisService;
    private final ThreadPoolExecutor executorPool;
    private final ThreadPoolExecutor consumerExecutorPool;
    private final BlockingQueue<StockSnapshot> queue;
    private final List<SnapshotConsumer> consumerList;
    private final List<SnapshotProducer> producerList;


    public TaskManager(List<String> symbolList, RedisService redisService){
        this.executorPool = ThreadPoolProducer.produceMainTheadPool();
        this.consumerExecutorPool = ThreadPoolProducer.produceCusumerTheadPool();
        this.queue = new LinkedBlockingQueue<>();
        this.redisService = redisService;
        this.symbolSet = new HashSet<>(symbolList);
        this.taskExecutors = taskExecutorProducer();
        this.consumerList = new ArrayList<>();
        this.producerList = new ArrayList<>();

        initConsumer();
        initProducer();
    }

    private void initConsumer(){
        for (int i = 0; i < CONSUMER_NUMBER; i++) {
            SnapshotConsumer snapshotConsumer = new SnapshotConsumer(queue, redisService);
            consumerList.add(snapshotConsumer);
        }
    }

    @SneakyThrows
    private void initProducer(){
        int totalNum = this.taskExecutors.size();
        int startIndex = 0;
        while (true){
            List<TaskExecutor> subTaskList = taskExecutors.subList(startIndex, Math.min(totalNum, startIndex + PRODUCER_ITEM_NUMBER));

            SnapshotProducer snapshotProducer = new SnapshotProducer(queue, subTaskList);
            System.out.println("init producer: " + subTaskList.size());
            sleep(50);
            producerList.add(snapshotProducer);
            if (startIndex+PRODUCER_ITEM_NUMBER >= totalNum){
                break;
            }
            startIndex += PRODUCER_ITEM_NUMBER;
        }
    }

    private List<TaskExecutor> taskExecutorProducer(){
        List<String> symbolList = new ArrayList<>(symbolSet);
        int taskPages = (int)Math.ceil(1.0 * symbolList.size() / TASK_MAX_COMPANY_NUMBER);
        List<TaskExecutor> taskExecutors = new ArrayList<>(taskPages);
        for (int i = 0; i < taskPages; i++) {
            int endIndex = Math.min(symbolList.size(),(i+1) * TASK_MAX_COMPANY_NUMBER );
            List<String> subSymbolList = symbolList.subList(i * TASK_MAX_COMPANY_NUMBER, endIndex);
            taskExecutors.add(new TaskExecutor(subSymbolList));
        }
        return taskExecutors;
    }


    @Override
    public void run() {
        System.out.println(this.getClass().getName() + "start");
        for(SnapshotConsumer consumer: consumerList){
            consumerExecutorPool.execute(consumer);
        }
        for (SnapshotProducer producer: producerList){
            executorPool.execute(producer);
        }
    }
}
