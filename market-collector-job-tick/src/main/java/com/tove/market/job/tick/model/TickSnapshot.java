package com.tove.market.job.tick.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class TickSnapshot {
    private OrderBook orderBook;
    /** 方向 up 1  down -1 no change 0*/
    private Integer arrow;

    /** 市场类型 SZ SH */
    private String type;
    /** 证券名称 */
    private String name;
    private String symbol;
    private String code;

    private String time;

    private Double high;
    private Double low;
    private Double open;
    private Double yestclose;
    /** 成交额 */
    private Long turnover;
    private Long volume;

    /** 涨跌幅 */
    private Double percent;
    /** 当前价格 */
    private Double price;
    /** 没懂 */
    private Integer status;

    private String update;
    /** */
    private Double updown;

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

        TickSnapshot tickSnapshot = TickSnapshot.builder().build();


        return null;
    }

    public static StockSnapshot coverTickToStock(TickSnapshot tickSnapshot){
        return null;
    }
}
