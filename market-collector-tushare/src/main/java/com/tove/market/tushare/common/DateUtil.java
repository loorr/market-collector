package com.tove.market.tushare.common;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
public class DateUtil {
    public static final String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String PATTERN_YYYY_MM_DD_NO_BLANK = "yyyyMMdd";

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

    public static List<String> getBetweenDateStrList(String startDate, String endDate, String patten){
        Date start = formatDate(startDate, patten);
        Date end = formatDate(endDate, patten);
        if (end.before(start)){
            log.error("input date rank error! start: {}, end: {}", startDate, endDate);
            return null;
        }

        List<String> list = new ArrayList<>();
        // 用Calendar 进行日期比较判断
        SimpleDateFormat sdf = new SimpleDateFormat(patten);
        Calendar calendar = Calendar.getInstance();
        while (start.getTime()<=end.getTime()){
            list.add(sdf.format(start));
            calendar.setTime(start);
            calendar.add(Calendar.DATE, 1);
            start=calendar.getTime();
        }
        return list;
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

        List<String> days = getBetweenDateStrList("20211209", "20220101", PATTERN_YYYY_MM_DD_NO_BLANK);
        log.info("end");
    }
}
