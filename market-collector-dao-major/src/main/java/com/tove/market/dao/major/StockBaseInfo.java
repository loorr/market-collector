package com.tove.market.dao.major;

import com.tove.market.common.BaseModel;
import lombok.Data;

@Data
public class StockBaseInfo extends BaseModel {

    /**
     * code
     */
    private String code;

    /**
     * name
     */
    private String name;
}
