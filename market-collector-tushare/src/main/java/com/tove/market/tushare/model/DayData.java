package com.tove.market.tushare.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author user
 */
@Data
@TableName(value = "day_data")
public class DayData {

    @TableId(type = IdType.AUTO)
    @TableField(value="`id`")
    private Long id;

    /**
     * 股票代码
     */
    @TableField(value="`symbol`")
    private String symbol;

    /**
     * 交易日期
     */
    @TableField(value="`trade_date`")
    private Date tradeDate;

    /**
     * 涨跌额
     */

    @TableField(value="`change_price`")
    private BigDecimal change;

    /**
     * 开盘价
     */
    @TableField(value="`open`")
    private BigDecimal open;

    /**
     * 最高价
     */
    @TableField(value="`high`")
    private BigDecimal high;

    /**
     * 最低价
     */
    @TableField(value="`low`")
    private BigDecimal low;

    /**
     * 收盘价
     */
    @TableField(value="`close`")
    private BigDecimal close;

    /**
     * 昨收价
     */
    @TableField(value="`pre_close`")
    private BigDecimal preClose;


    /**
     * 涨跌幅
     */
    @TableField(value = "`pct_chg`")
    private BigDecimal pctChg;

    /**
     * 成交量
     */
    @TableField(value="`vol`")
    private BigDecimal vol;

    /**
     * 成交额
     */
    @TableField(value="`amount`")
    private BigDecimal amount;

    public DayData() {}
}