package com.tove.market.tushare.request;

import lombok.Data;

/**
 * @author user
 */
@Data
public class TushareParamsRet {
    private static final String PRIVATE_TOKEN = "4f0dee68a9ce67733efe90fd06e291664f0b52e40e5a5cb7843a8ed8";

    private String api_name;
    private String token = PRIVATE_TOKEN;
    private String params;
    private String fields="";
}
