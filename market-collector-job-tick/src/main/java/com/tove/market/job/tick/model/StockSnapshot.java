package com.tove.market.job.tick.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@EqualsAndHashCode
public class StockSnapshot implements Serializable {
    /** 方向 */

    private String arrow;

    private Double ask1;
    private Double ask2;
    private Double ask3;
    private Double ask4;
    private Double ask5;


    private Integer askvol1;
    private Integer askvol2;
    private Integer askvol3;
    private Integer askvol4;
    private Integer askvol5;


    private Double bid1;
    private Double bid2;
    private Double bid3;
    private Double bid4;
    private Double bid5;

    private Integer bidvol1;
    private Integer bidvol2;
    private Integer bidvol3;
    private Integer bidvol4;
    private Integer bidvol5;
    private String code;

    private Double high;
    private Double low;
    /** 证券名称 */
    private String name;
    private Double open;
    /** 涨跌幅 */
    private Double percent;
    /** 当前价格 */
    private Double price;
    /** 没懂 */
    private Integer status;
    private String symbol;
    private String time;
    /** 成交额 */
    private Long turnover;
    /** 市场类型 SZ SH */
    private String type;
    private String update;
    /** */
    private Double updown;
    private Long volume;
    private Double yestclose;

    @Override
    public String toString(){
        return JSON.toJSONString(this);
    }

    public static SnapshotProto.Tick covertSnapshotProtoTick(StockSnapshot ss){
        SnapshotProto.OrderBook orderBook = SnapshotProto.OrderBook
                .newBuilder()
                .addAllBid(
                        new ArrayList(){{
                            add(ss.getBid1());
                            add(ss.getBid2());
                            add(ss.getBid3());
                            add(ss.getBid4());
                            add(ss.getBid5());
                        }}
                )
                .addAllAsk(
                        new ArrayList(){{
                            add(ss.getAsk1());
                            add(ss.getAsk2());
                            add(ss.getAsk3());
                            add(ss.getAsk4());
                            add(ss.getAsk5());
                        }}
                )
                .addAllBidVol(
                        new ArrayList(){{
                            add(ss.getBidvol1());
                            add(ss.getBidvol2());
                            add(ss.getBidvol3());
                            add(ss.getBidvol4());
                            add(ss.getBidvol5());
                        }}
                )
                .addAllAskVol(
                        new ArrayList(){{
                            add(ss.getAskvol1());
                            add(ss.getAskvol2());
                            add(ss.getAskvol3());
                            add(ss.getAskvol4());
                            add(ss.getAskvol5());
                        }}
                )
                .build();

        SnapshotProto.Tick tick = SnapshotProto.Tick
                .newBuilder()
                .setOrderBook(orderBook)
                .setArrow(ss.getArrow())
                .setCode(ss.getCode())
                .setHigh(ss.getHigh())
                .setLow(ss.getLow())
                .setName(ss.getName())
                .setOpen(ss.getOpen())
                .setPercent(ss.getPercent())
                .setPrice(ss.getPrice())
                .setStatus(ss.getStatus())
                .setSymbol(ss.getSymbol())
                .setTime(ss.getTime())
                .setTurnover(ss.getTurnover())
                .setType(ss.getType())
                .setUpdate(ss.getUpdate())
                .setUpdown(ss.getUpdown())
                .setVolume(ss.getVolume())
                .setYestclose(ss.getYestclose())
                .build();
        return tick;
    }

    public static void main(String[] args) {
        StockSnapshot snapshot = new StockSnapshot();
        snapshot.setAsk1(1.00);

        System.out.println(snapshot.toString());
    }
}
