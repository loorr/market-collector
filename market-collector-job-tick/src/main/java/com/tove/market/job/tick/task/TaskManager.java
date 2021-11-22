package com.tove.market.job.tick.task;
import com.tove.market.job.tick.ThreadPoolProducer;
import com.tove.market.job.tick.TimeMonitor;
import com.tove.market.job.tick.model.StockSnapshot;
import com.tove.market.job.tick.service.RedisService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import static java.lang.Thread.sleep;

@Log4j2
public class TaskManager implements Runnable {
    /** 每个TaskExecutor最大的公司数量, 也就是一个人url的公司数量 */
    public static final Integer TASK_MAX_COMPANY_NUMBER = 40;
    /** 消费者数量*/
    private final Integer CONSUMER_NUMBER = 2;
    /** 每个生产者的 TaskExecutor 数量 */
    private final Integer PRODUCER_ITEM_NUMBER = 6;

    private final Set<String> symbolSet;
    private final List<TaskExecutor> taskExecutors;
    private final RedisService redisService;
    private final ThreadPoolExecutor producerExecutorPool;
    private final ThreadPoolExecutor consumerExecutorPool;
    private final BlockingQueue<StockSnapshot> queue;
    private final List<SnapshotConsumer> consumerList;
    private final List<SnapshotProducer> producerList;
    private final TimeMonitor timeMonitor = new TimeMonitor();

    public TaskManager(List<String> symbolList, RedisService redisService){
        this.producerExecutorPool = ThreadPoolProducer.produceMainTheadPool();
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

            SnapshotProducer snapshotProducer = new SnapshotProducer(String.valueOf(startIndex), timeMonitor, queue, subTaskList);
            System.out.println("init producer: " + subTaskList.size());
            producerList.add(snapshotProducer);

            sleep(100);
            if (startIndex+PRODUCER_ITEM_NUMBER >= totalNum){
                break;
            }
            startIndex += PRODUCER_ITEM_NUMBER;
        }
        timeMonitor.setTotalProducerNum(producerList.size());
    }

    private List<TaskExecutor> taskExecutorProducer(){
        List<String> symbolList = new ArrayList<>(symbolSet);

        int taskPages = (int)Math.ceil(1.0 * symbolList.size() / TASK_MAX_COMPANY_NUMBER);
        log.info("TaskExecutor sum num: {}", taskPages);
        List<TaskExecutor> taskExecutors = new ArrayList<>(taskPages);
        for (int i = 0; i < taskPages; i++) {
            int endIndex = Math.min(symbolList.size(),(i+1) * TASK_MAX_COMPANY_NUMBER );
            List<String> subSymbolList = symbolList.subList(i * TASK_MAX_COMPANY_NUMBER, endIndex);
            taskExecutors.add(new TaskExecutor(subSymbolList));
        }
        return taskExecutors;
    }

    @SneakyThrows
    @Override
    public void run() {
        System.out.println(this.getClass().getName() + "start");
        for(SnapshotConsumer consumer: consumerList){
            sleep(300);
            consumerExecutorPool.execute(consumer);
        }
        while (true){
            for (SnapshotProducer producer: producerList){
                //System.out.println("before start : " + producer.getName());
                if (!producer.checkCanGet()){
                    continue;
                }
                // sleep(700/producerList.size());
                //System.out.println("start : " + producer.getName());
                producerExecutorPool.execute(producer);
            }
            sleep(300);
        }

    }
}
