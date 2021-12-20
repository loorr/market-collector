package com.tove.market.tushare.request;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResultData {
    private Boolean has_more;
    private List<String> fields;
    private List<JSONArray> items;
}
