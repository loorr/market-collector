package com.tove.market.tushare.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * @author user
 */
public class TushareApi {
    private static final String TARGET_URL = "http://api.tushare.pro";

    private static final CloseableHttpClient HTTP_CLIENT = HttpClients.createDefault();
    private static final RequestConfig REQUEST_CONFIG = RequestConfig.custom()
                                                        .setConnectTimeout(3 * 1000)
                                                        .setSocketTimeout(10 * 1000).build();

    private TushareResult doPost(TushareParamsRet tushareParamsRet){
        HttpPost httpPost = new HttpPost(TARGET_URL);
        httpPost.setHeader("Content-type","application/json; charset=utf-8");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setConfig(REQUEST_CONFIG);
        String parameter = JSON.toJSONString(tushareParamsRet, SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
        System.out.println(parameter);
        httpPost.setEntity(new StringEntity(parameter, Charset.forName("UTF-8")));

        CloseableHttpResponse response = null;
        String result = null;
        try {
            response = HTTP_CLIENT.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            result = EntityUtils.toString(httpEntity, Charset.forName("UTF-8"));
            TushareResult tushareResult = JSON.parseObject(result, TushareResult.class);
            return tushareResult;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public TushareResult getStockBasic(){
        TushareParamsRet ret = new TushareParamsRet();
        ret.setApi_name(ApiNameEnum.STOCK_BASE.getName());
        ret.setParams(new HashMap<>());
        ret.setFields("symbol,name,area,industry,market,list_date,fullname,enname,cnspell,exchange,curr_type,list_status,delist_date,is_hs");
        return doPost(ret);
    }

    public TushareResult getDailyData(){
        return null;
    }
}