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

    public static String getTushareCode(String symbol){
        if (symbol.startsWith("6")){
            return connectCharUseDot(symbol, ExchangeEnum.SSE.getShortName());
        }else if (symbol.startsWith("3") || symbol.startsWith("0")){
            return connectCharUseDot(symbol, ExchangeEnum.SZSE.getShortName());
        }else if (symbol.startsWith("4") || symbol.startsWith("8")){
            return connectCharUseDot(symbol, ExchangeEnum.BSE.getShortName());
        }
        return null;
    }


    private static String connectCharUseDot(String symbol, String name){
        return symbol + "." + name;
    }

}
