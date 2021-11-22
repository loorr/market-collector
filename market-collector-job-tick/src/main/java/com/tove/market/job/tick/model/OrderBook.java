package com.tove.market.job.tick.model;

import lombok.Data;


import java.util.List;

@Data
public class OrderBook {
    private List<Double> bidList;
    private List<Double> askList;
    private List<Integer> askVol;
    private List<Integer> bidVol;
}
