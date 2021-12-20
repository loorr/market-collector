package com.tove.market.tushare.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author user
 */
@Data
public class DayData {

    @TableId(type = IdType.AUTO)
    /**
     * 主键
     */
    private Long id;

    /**
     * 股票代码
     */
    private String symbol;

    /**
     * 交易日期
     */
    private Date tradeDate;

    /**
     * 开盘价
     */
    private BigDecimal open;

    /**
     * 最高价
     */
    private BigDecimal high;

    /**
     * 最低价
     */
    private BigDecimal low;

    /**
     * 收盘价
     */
    private BigDecimal close;

    /**
     * 昨收价
     */
    private BigDecimal preClose;

    /**
     * 涨跌额
     */
    private BigDecimal change;

    /**
     * 涨跌幅
     */
    private BigDecimal pctChg;

    /**
     * 成交量
     */
    private BigDecimal vol;

    /**
     * 成交额
     */
    private BigDecimal amount;

    public DayData() {}
}