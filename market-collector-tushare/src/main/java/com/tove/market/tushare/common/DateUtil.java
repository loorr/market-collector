package com.tove.market.tushare.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";

    public static Boolean isDateVail(String date, String pattenrn) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattenrn);
        boolean flag = true;
        try {
            // Java 8 新添API 用于解析日期和时间
            LocalDateTime.parse(date, dtf);
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static void main(String[] args) {
        System.out.println(isDateVail("2021-09-12", PATTERN_YYYY_MM_DD));
    }
}
