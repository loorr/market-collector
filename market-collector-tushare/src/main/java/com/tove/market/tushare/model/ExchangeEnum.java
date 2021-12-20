package com.tove.market.tushare.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExchangeEnum {
    SSE("上交所", "SH"),
    SZSE("深交所", "SZ"),
    BSE("北交所","BJ"),
    HKEX("港交所", "HK")
    ;

    private String zhName;
    private String shortName;

}
