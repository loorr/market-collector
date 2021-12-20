package com.tove.market.tushare.request;

import lombok.Data;

@Data
public class TushareResult<T> {
    private String request_id;
    private Integer code;
    private String msg;
    private T data;
}
