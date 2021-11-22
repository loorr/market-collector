package com.tove.market.job.tick.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;


import java.util.List;

@Data
public class OrderBook {
    @JSONField(name = "bl")
    private List<Double> bidList;
    @JSONField(name = "al")
    private List<Double> askList;
    @JSONField(name = "av")
    private List<Integer> askVol;
    @JSONField(name = "bv")
    private List<Integer> bidVol;

    @Override
    public String toString(){
        return JSON.toJSONString(this);
    }
}
