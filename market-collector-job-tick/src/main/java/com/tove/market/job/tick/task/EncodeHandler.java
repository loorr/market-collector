package com.tove.market.job.tick.task;

import com.tove.market.job.tick.model.StockSnapshot;

import java.lang.reflect.Field;

/**
 * 编码
 */
public class EncodeHandler {


    public static String decodeStockSnapshot(StockSnapshot stockSnapshot){
        Field [] fields = refrect(stockSnapshot);
        for(int i=0; i<fields.length; i++){
            Field f = fields[i];
            f.setAccessible(true);
            try {
                System.out.println("host["+f.getName()+"] =  "+f.get(stockSnapshot));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //获得所有属性数组
    public static Field[] refrect(StockSnapshot host){
        Class cls = host.getClass();
        Field[] fields = cls.getDeclaredFields();
        return fields;
    }
}
