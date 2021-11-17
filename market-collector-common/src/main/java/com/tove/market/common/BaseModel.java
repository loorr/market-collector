package com.tove.market.common;

import lombok.Data;

import java.util.Date;

@Data
public abstract class BaseModel {
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
}
