package com.tove.market.tushare.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author user
 */
@Data
@NoArgsConstructor
public class SynTask {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 任务标识
     */
    private String name;

    /**
     * 任务类型
     */
    private String type;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 任务状态
     */
    private String state;

    private String errorLog;

    public static SynTask createNewTask(String taskName, String taskType){
        SynTask task = new SynTask();
        task.setName(taskName);
        task.setType(taskType);
        task.setStartTime(new Date());
        task.setState(TaskStateEnum.INIT.getState());
        return task;
    }


    @Getter
    @AllArgsConstructor
    public enum TaskStateEnum{

        INIT("init"),
        RUNNING("running"),
        COMPLETED("completed"),
        ERROR_PAUSED("error paused")
        ;

        private String state;
    }


    @Getter
    @AllArgsConstructor
    public enum TaskNameEnum{
        SYMBOL_ALL_DAY_DATA("%s - all day data"),
        DATE_DAY_DATA("%s - day data by date"),
        ;

        private String nameTemplate;

        public String getFormatName(String ...args){
            int num = StringUtils.countOccurrencesOf(nameTemplate, "%");
            if (num != args.length){
                return null;
            }
            return String.format(nameTemplate, args);
        }
    }

    @Getter
    @AllArgsConstructor
    public enum TaskTypeEnum{

        DAY_DATA_ALL("纵向,同步所有历史日线数据"),
        DAY_DATA_BY_DAY("横向,按天同步日线数据"),
        ;

        private String typeName;
    }
}

