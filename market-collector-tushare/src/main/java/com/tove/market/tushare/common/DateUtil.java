package com.tove.market.tushare.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static final String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";

    public static Boolean isDateVail(String date, String patten) {
        DateTimeFormatter format = new DateTimeFormatterBuilder()
                .appendPattern(patten)
                .toFormatter();
        boolean flag = true;
        try {
            LocalDate.parse(date, format);
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }


    public static String formatDate(Date date, String patten){
        SimpleDateFormat sdf = new SimpleDateFormat(patten);
        return sdf.format(date);
    }

    public static Date formatDate(String date, String patten){
        SimpleDateFormat sdf = new SimpleDateFormat(patten);
        Date ans = null;
        try {
            ans = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ans;
    }

    public static String subAndAddDate(Date date, String patten, int n){
        SimpleDateFormat sdf=new SimpleDateFormat(patten);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.DAY_OF_YEAR,n);
        return formatDate(rightNow.getTime(), patten);
    }

    public static String subAndAddDate(String date, String patten, int n){
        SimpleDateFormat sdf=new SimpleDateFormat(patten);
        try {
            Date dt=sdf.parse(date);
            return subAndAddDate(dt, patten, n);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 计算两个日期之间的天数
     * @param start
     * @param end
     * @return
     */
    public static long getBetweenDays(Date start, Date end){
        return ChronoUnit.DAYS.between(dateToLocalDateTime(start), dateToLocalDateTime(end));
    }

    public static long getBetweenSeconds(Date start, Date end){
        return ChronoUnit.SECONDS.between(dateToLocalDateTime(start), dateToLocalDateTime(end));
    }

    public static LocalDateTime dateToLocalDateTime(Date date){
        return date.toInstant()
                .atZone( ZoneId.systemDefault() )
                .toLocalDateTime();
    }

    public static void main(String[] args) {
        System.out.println(isDateVail("2021-09-12", PATTERN_YYYY_MM_DD));
        System.out.println(formatDate(new Date(), PATTERN_YYYY_MM_DD));
        System.out.println(subAndAddDate("2021-09-30", PATTERN_YYYY_MM_DD, 1));

        long diff = getBetweenDays(
                formatDate("2021-09-12", PATTERN_YYYY_MM_DD),
                formatDate("2021-09-12", PATTERN_YYYY_MM_DD)
        );
        System.out.printf(String.valueOf(diff));

    }
}
