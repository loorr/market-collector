package com.tove.market.tushare.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @author user
 */
@Data
public class TradeCal {
    @TableId(type = IdType.AUTO)
    /**
     * 主键
     */
    private Long id;

    /**
     * 交易所
     */
    private String exchange;

    /**
     * 交易日期
     */
    private Date calDate;

    /**
     * 是否开盘
     */
    private int isOpen;

    public TradeCal() {}
}
