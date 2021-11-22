package com.tove.market.job.tick;

import com.alibaba.fastjson.JSON;
import com.tove.market.job.tick.model.SnapshotProto;
import com.tove.market.job.tick.model.StockSnapshot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Probuf Test")
public class TestProbuf {

    @Test
    void test(){
        SnapshotProto.OrderBook orderBook = SnapshotProto.OrderBook
                .newBuilder()
                //.addAllAsk()
                .build();
        SnapshotProto.Tick tick = SnapshotProto.Tick
                .newBuilder()
                .setOrderBook(orderBook)
                .setName("12")

                .build();

        StockSnapshot ss = new StockSnapshot();
        ss.setAsk1(12.22);
        System.out.println(JSON.toJSON(ss));
    }
}
