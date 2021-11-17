package com.tove.market.job.tick;

import com.tove.market.job.tick.common.LifeCycle;
import com.tove.market.job.tick.service.RedisService;
import com.tove.market.job.tick.task.TaskManager;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

/**
 * tick 行情收集器
 */
@Log4j2
public class JobTickApplication implements LifeCycle {

    private final TickApplicationContent content= new TickApplicationContent();

    public JobTickApplication(){}

    public static void main(String[] args) {
        JobTickApplication jobTickApplication = new JobTickApplication();
        jobTickApplication.start();
    }

    @Override
    public void start() {
        RedisService redisService = content.getRedisService();
        List<String> symbolList = new ArrayList<>();
        symbolList.addAll(redisService.getSHCompanysSymbol());
        symbolList.addAll(redisService.getSZCompanysSymbol());
        System.out.println(" List Size: " + symbolList.size());
        TaskManager sZTaskManager = new TaskManager(symbolList, redisService);
        content.getMainThreadPool().execute(sZTaskManager);
    }

    @Override
    public void stop() {

    }

    @Override
    public void running() {

    }
}
