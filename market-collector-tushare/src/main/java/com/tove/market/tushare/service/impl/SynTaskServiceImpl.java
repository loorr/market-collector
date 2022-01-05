package com.tove.market.tushare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tove.market.tushare.dao.SynTaskMapper;
import com.tove.market.tushare.model.SynTask;
import com.tove.market.tushare.service.DayDataService;
import com.tove.market.tushare.service.StockBasicService;
import com.tove.market.tushare.service.SynTaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class SynTaskServiceImpl extends ServiceImpl<SynTaskMapper, SynTask> implements SynTaskService {

    @Resource
    private StockBasicService stockBasicService;

    @Resource
    private DayDataService dayDataService;

    @Override
    public SynTask getSynTaskByNameAndType(String name, String type){
        LambdaQueryWrapper<SynTask> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(SynTask::getName, name);
        lambdaQueryWrapper.eq(SynTask::getType, type);
        return this.getOne(lambdaQueryWrapper);
    }

    @Override
    public SynTask getOrCreateSynTaskByNameAndType(String name, String type){
        SynTask task = getSynTaskByNameAndType(name, type);
        if (task == null){
            task = SynTask.createNewTask(name,type);
        }
        return task;
    }

    public void updateTaskState(String name, String type, SynTask.TaskStateEnum stateEnum){
        UpdateWrapper<SynTask> taskUpdateWrapper = new UpdateWrapper<>();
        taskUpdateWrapper.eq("name", name);
        taskUpdateWrapper.eq("type", type);
        taskUpdateWrapper.set("state", stateEnum.getState());
        this.update(taskUpdateWrapper);
    }

    public void openDayDataTask(String symbol){
        String taskName = SynTask.TaskNameEnum.SYMBOL_ALL_DAY_DATA.getFormatName(symbol);
        String taskType = SynTask.TaskTypeEnum.DAY_DATA_ALL.getTypeName();
        SynTask task = getSynTaskByNameAndType(taskName, taskType);
        if (task == null){
            task = new SynTask();
            task.setName(taskName);
            task.setType(taskType);
        }
        task.setStartTime(new Date());
        task.setState(SynTask.TaskStateEnum.RUNNING.getState());
        this.saveOrUpdate(task);
    }
}
