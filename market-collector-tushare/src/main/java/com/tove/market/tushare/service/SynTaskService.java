package com.tove.market.tushare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tove.market.tushare.model.SynTask;


public interface SynTaskService extends IService<SynTask> {
    SynTask getSynTaskByNameAndType(String name, String type);

    SynTask getOrCreateSynTaskByNameAndType(String name, String type);
}
