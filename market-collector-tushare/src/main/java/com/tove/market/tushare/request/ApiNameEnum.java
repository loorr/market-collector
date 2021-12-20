package com.tove.market.tushare.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author user
 */

@Getter
@AllArgsConstructor
public enum ApiNameEnum {
    STOCK_BASE("stock_basic"),
    DAILY("daily")
    ;

    private String name;
}
