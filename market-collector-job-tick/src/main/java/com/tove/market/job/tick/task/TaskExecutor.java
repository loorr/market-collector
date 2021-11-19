package com.tove.market.job.tick.task;

import com.alibaba.fastjson.JSONObject;
import com.tove.market.job.tick.common.HttpClientUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static jodd.util.ThreadUtil.sleep;

public class TaskExecutor {
    private static final String BASE_URL = "http://api.money.126.net/data/feed/";
    private final String taskUrl;
    private Date date;
    public String name;

    public TaskExecutor(List<String> companyList){
        this.taskUrl = getTaskUrl(companyList);
    }

    private Boolean checkCanGet(){
        if (date == null){
            date = new Date();
            return true;
        }
        return new Date().getTime() - date.getTime() >= 3 * 1000;
    }

    public List<StockSnapshot> getStockSnapshot(){
        if (!checkCanGet()){
            return null;
        }

        String data = HttpClientUtil.doGet(this.taskUrl);
        String removeBracket = patternContent(data);
        JSONObject jsonObject = (JSONObject) JSONObject.parse(removeBracket);
        if (jsonObject == null){
            System.out.println("getStockSnapshot 错误");
        }
        List<StockSnapshot> stockSnapshotList = new ArrayList<>();
        for (String key: jsonObject.keySet()){
            StockSnapshot snapshot = jsonObject.getObject(key, StockSnapshot.class);
            stockSnapshotList.add(snapshot);
        }
        return stockSnapshotList;
    }

    private String getTaskUrl(List<String> companyList){
        final StringBuffer sb = new StringBuffer(companyList.size()*8);
        for (String symbol: companyList) {
            if (name == null){
                name = symbol;
            }
            if (symbol.charAt(0) == '6'){
                sb.append("0" + symbol);
            }else if (symbol.charAt(0) == '0' || symbol.charAt(0) == '3'){
                sb.append("1" + symbol);
            }
            sb.append(",");
        }
        return BASE_URL + sb.toString();
    }

    private String patternContent(String input){
        String[] s = input.split("\\(|\\)");
        if (s.length != 3) {
            return null;
        }
        return s[1];
    }

    // just test
    public static void main(String[] args) {
        String[] s = {"601398","002392"};
        List<String> list = Arrays.asList(s);
        TaskExecutor taskExecutor = new TaskExecutor(list);
        List<StockSnapshot>[] sum = new List[10];
        for (int i = 0; i < 10; i++) {
            sleep(1000);
            List<StockSnapshot> result = taskExecutor.getStockSnapshot();
            sum[i] = result;
        }

        System.out.println("12");
    }
}
