package com.tove.market.common;

public final class Constant {
    /** commom */
    public static final String SH_COMPANY_KEY = "company:sh";
    public static final String SZ_COMPANY_KEY = "company:sz";

    /** tick */
    public static final String TICK_DATE_SYMBOL = "tick:%s:%s";


    public static String getKey(String key, String... args){
        return String.format(key, args);
    }


}
