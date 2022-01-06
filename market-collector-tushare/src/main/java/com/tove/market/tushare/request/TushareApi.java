package com.tove.market.tushare.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @author user
 */
public class TushareApi {
    private static final String TARGET_URL = "http://api.tushare.pro";

    private static final CloseableHttpClient HTTP_CLIENT = HttpClients.createDefault();
    private static final RequestConfig REQUEST_CONFIG = RequestConfig.custom()
                                                        .setConnectTimeout(5 * 1000)
                                                        .setSocketTimeout(20 * 1000).build();

    private TushareResult doPost(TushareParamsRet tushareParamsRet) throws SocketTimeoutException {
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
            return checkResult(tushareResult);
        } catch (SocketTimeoutException e){
            throw e;
        } catch (IOException  e) {
            e.printStackTrace();
        }
        return null;
    }


    public TushareResult getStockBasic() throws SocketTimeoutException {
        TushareParamsRet ret = new TushareParamsRet();
        ret.setApi_name(ApiNameEnum.STOCK_BASE.getName());
        ret.setParams(new HashMap<>());
        ret.setFields("symbol,name,area,industry,market,list_date,fullname,enname,cnspell,exchange,curr_type,list_status,delist_date,is_hs");
        return doPost(ret);
    }

    public TushareResult getDailyData(String tsCode, String startDate, String endDate) throws SocketTimeoutException {
        TushareParamsRet ret = new TushareParamsRet();
        ret.setApi_name(ApiNameEnum.DAILY.getName());
        Map<String,String> params = new HashMap<>();
        params.put("ts_code", tsCode);
        params.put("start_date", startDate);
        params.put("end_date", endDate);
        ret.setParams(params);
        return doPost(ret);
    }

    public TushareResult getDailyData(String tradeDate) throws SocketTimeoutException {
        TushareParamsRet ret = new TushareParamsRet();
        ret.setApi_name(ApiNameEnum.DAILY.getName());
        Map<String,String> params = new HashMap<>();
        params.put("trade_date", tradeDate);
        ret.setParams(params);
        return doPost(ret);
    }

    private TushareResult checkResult(TushareResult tushareResult ){
        if (tushareResult.getCode() == 0){
            return tushareResult;
        }
        System.out.printf("TushareResult Error: " + tushareResult.getMsg());
        return null;
    }
}
