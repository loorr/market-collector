package com.tove.market.tushare.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;


/**
 * @author user
 */
@Data
public class StockBasic  {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 股票代码
     */
    private String symbol;

    /**
     * 股票名称
     */
    private String name;

    /**
     * 地域
     */
    private String area;

    /**
     * 所属行业
     */
    private String industry;

    /**
     * 股票全称
     */
    private String fullName;

    /**
     * 英文全称
     */
    private String enName;

    /**
     * 拼音缩写
     */
    private String cnSpell;

    /**
     * 市场类型
     */
    private String market;

    /**
     * 交易所
     */
    private String exchange;

    /**
     * 交易货币
     */
    private String currType;

    /**
     * 上市状态 l上市 d退市 p暂停上市
     */
    private String listStatus;

    /**
     * 上市日期
     */
    private Date listDate;

    /**
     * 退市日期
     */
    private Date delistDate;

    /**
     * 是否沪深港通标的，n否 h沪股通 s深股通
     */
    private String isHs;

    public StockBasic() {}
}