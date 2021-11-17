package com.tove.market.dao.temp.model;

import com.tove.market.common.BaseModel;
import lombok.Data;

@Data
public class StockInfoACodeName extends BaseModel {
    /**
     * code
     */
    private String code;

    /**
     * name
     */
    private String name;
}
