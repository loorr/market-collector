package com.tove.market.dao.major;

import lombok.Data;

import java.util.Date;

@Data
public class StockBaseInfo {
    /**
     * id
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date dbCreateTime;

    /**
     * 更新时间
     */
    private Date dbModifyTime;

    /**
     * code
     */
    private String code;

    /**
     * name
     */
    private String name;
}
