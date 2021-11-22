package com.tove.market.job.tick.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class TickSnapshot {
    @JSONField(name = "ob")
    private OrderBook orderBook;
    /** 方向 up 1  down -1 no change 0*/
    @JSONField(name = "ar")
    private Integer arrow;

    /** 市场类型 SZ SH */
    @JSONField(name = "ty")
    private String type;
    /** 证券名称 */
    @JSONField(name = "n")
    private String name;
    @JSONField(name = "s")
    private String symbol;
    @JSONField(name = "c")
    private String code;
    @JSONField(name = "t")
    private String time;
    @JSONField(name = "h")
    private Double high;
    @JSONField(name = "l")
    private Double low;
    @JSONField(name = "o")
    private Double open;
    @JSONField(name = "yc")
    private Double yestclose;
    /** 成交额 */
    @JSONField(name = "tu")
    private Long turnover;
    @JSONField(name = "v")
    private Long volume;

    /** 涨跌幅 */
    @JSONField(name = "pe")
    private Double percent;
    /** 当前价格 */
    @JSONField(name = "pr")
    private Double price;
    /** 没懂 */
    @JSONField(name = "st")
    private Integer status;

    @JSONField(name = "ue")
    private String update;
    /** */
    @JSONField(name = "ud")
    private Double updown;

    @Override
    public String toString(){
        return JSON.toJSONString(this);
    }


    public static TickSnapshot covertStockToTick(StockSnapshot stockSnapshot){
        List<Double> bidList = new ArrayList(){{
            add(stockSnapshot.getBid1());
            add(stockSnapshot.getBid2());
            add(stockSnapshot.getBid3());
            add(stockSnapshot.getBid4());
            add(stockSnapshot.getBid5());
        }};
        List<Double> askList = new ArrayList(){{
            add(stockSnapshot.getAsk1());
            add(stockSnapshot.getAsk2());
            add(stockSnapshot.getAsk3());
            add(stockSnapshot.getAsk4());
            add(stockSnapshot.getAsk5());
        }};
        List<Integer> askVol = new ArrayList(){{
            add(stockSnapshot.getAskvol1());
            add(stockSnapshot.getAskvol2());
            add(stockSnapshot.getAskvol3());
            add(stockSnapshot.getAskvol4());
            add(stockSnapshot.getAskvol5());
        }};
        List<Integer> bidVol = new ArrayList(){{
            add(stockSnapshot.getBidvol1());
            add(stockSnapshot.getBidvol2());
            add(stockSnapshot.getBidvol3());
            add(stockSnapshot.getBidvol4());
            add(stockSnapshot.getBidvol5());
        }};

        OrderBook orderBook = new OrderBook();
        orderBook.setAskList(askList);
        orderBook.setAskVol(askVol);
        orderBook.setBidList(bidList);
        orderBook.setBidVol(bidVol);

        TickSnapshot tickSnapshot = TickSnapshot.builder()
                .arrow(TickSnapshot.arrowTransfer(stockSnapshot.getArrow()))
                .code(stockSnapshot.getCode())
                .high(stockSnapshot.getHigh())
                .low(stockSnapshot.getLow())
                .name(stockSnapshot.getName())
                .open(stockSnapshot.getOpen())
                .percent(stockSnapshot.getPercent())
                .price(stockSnapshot.getPrice())
                .status(stockSnapshot.getStatus())
                .symbol(stockSnapshot.getSymbol())
                .time(stockSnapshot.getTime())
                .turnover(stockSnapshot.getTurnover())
                .type(stockSnapshot.getType())
                .update(stockSnapshot.getUpdate())
                .updown(stockSnapshot.getUpdown())
                .volume(stockSnapshot.getVolume())
                .yestclose(stockSnapshot.getYestclose())
                .orderBook(orderBook)
                .build();
        return tickSnapshot;
    }

    public static StockSnapshot coverTickToStock(TickSnapshot tickSnapshot){
        return null;
    }

    private static Integer arrowTransfer(String arrow){
        return 0;
    }
}
