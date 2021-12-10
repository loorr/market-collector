package com.tove.market.job.tick.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {
    private static final String START_TIME = "9:15";
    private static final String AFTER_TIME = "13:00";
    private static final String FOREMAT_DATE_BLANK = "yyyy-MM-dd ";
    private static final String FOREMAT_DATE_MINS = "yyyy-MM-dd HH:mm";
    private static final SimpleDateFormat myFmt = new SimpleDateFormat(FOREMAT_DATE_BLANK);
    private static final SimpleDateFormat sdf = new SimpleDateFormat(FOREMAT_DATE_MINS);


    public static boolean isEnd(){
        while (true){
            Calendar calendar = Calendar.getInstance();
            System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
            System.out.println(calendar.get(Calendar.MINUTE));
            return calendar.get(Calendar.HOUR_OF_DAY)>15 && calendar.get(Calendar.MINUTE) > 0;
        }
    }

    public static long diffTradeTime(Calendar calendar){
        try {
            String currDate = myFmt.format(new Date());
            String moringOpentime = currDate + START_TIME;
            Date moringDate = sdf.parse(moringOpentime);
            long currTimeMillis = System.currentTimeMillis();
            long diffMoring = moringDate.getTime() - currTimeMillis;
            if (diffMoring >= 0){
                return diffMoring;
            }
            String afterOpenTime = currDate + AFTER_TIME;
            Date afterDate = sdf.parse(afterOpenTime);
            return afterDate.getTime() - currTimeMillis;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean isWorkDay(Calendar calendar){
        return Calendar.SATURDAY != calendar.get(Calendar.DAY_OF_WEEK)
                && Calendar.SUNDAY != calendar.get(Calendar.DAY_OF_WEEK);
    }


    public static void main(String[] args) {
        System.out.println(1.0*diffTradeTime(Calendar.getInstance())/1000/60);
        System.out.println(isWorkDay(Calendar.getInstance()));
    }

}
