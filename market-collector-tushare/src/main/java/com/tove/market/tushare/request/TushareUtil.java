package com.tove.market.tushare.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.base.CaseFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TushareUtil {

    public static <T> List<T> converTushareResult(List<TushareResult> tushareResultList, Class<T> clazz){
        List<HashMap<String, Object>> list = converTushareResult(tushareResultList);
        List<T> result = list.stream().map(o->{
            T t = JSON.parseObject(JSON.toJSONString(o), clazz);
            return t;
        }).collect(Collectors.toList());
        return  result;
    }

    public static List<HashMap<String, Object>>  converTushareResult(List<TushareResult> tushareResultList){
        List<HashMap<String, Object>> ans = new ArrayList<>();
        for (TushareResult tushareResult: tushareResultList){
            ans.addAll(converTushareResult(tushareResult));
        }
        return ans;
    }

    public static List<HashMap<String, Object>>  converTushareResult(TushareResult tushareResult){
        List<String> fileds = lowerUnderscoreToLowerCamel(tushareResult.getData().getFields());
        List<JSONArray> data = tushareResult.getData().getItems();

        int filedLen = fileds.size();
        List<HashMap<String, Object>> ans = new ArrayList<>(data.size());

        for (JSONArray item: data){
            HashMap<String, Object> mapItem = new HashMap<>(filedLen);
            for (int i = 0; i < filedLen; i++) {
                mapItem.put(fileds.get(i), item.get(i));
            }
            ans.add(mapItem);
        }
        return ans;
    }

    public static List<String> lowerUnderscoreToLowerCamel(List<String> data){
        return data.stream().map(o-> CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, o)).collect(Collectors.toList());
    }
}
