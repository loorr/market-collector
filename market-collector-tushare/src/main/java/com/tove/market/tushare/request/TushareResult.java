package com.tove.market.tushare.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TushareResult {
    private String request_id;
    private Integer code;
    private String msg;
    private ResultData data;
}
