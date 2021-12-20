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

/**
 * @author user
 */
public class TushareApi {
    private static final String TARGET_URL = "http://api.tushare.pro";

    private static final CloseableHttpClient HTTP_CLIENT = HttpClients.createDefault();
    private static final RequestConfig REQUEST_CONFIG = RequestConfig.custom()
                                                        .setConnectTimeout(3 * 1000)
                                                        .setSocketTimeout(10 * 1000).build();

    private String doPost(TushareParamsRet tushareParamsRet){
        HttpPost httpPost = new HttpPost(TARGET_URL);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setConfig(REQUEST_CONFIG);

        String parameter = JSON.toJSONString(tushareParamsRet, SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);

        System.out.println(parameter);
//        String parameter = "{\n    \"api_name\": \"stock_basic\",\n    \"token\": \"4f0dee68a9ce67733efe90fd06e291664f0b52e40e5a5cb7843a8ed8\",\n    \"parans\": {},\n    \"fields\": \"\"\n}";
        StringEntity se = new StringEntity(parameter, Charset.forName("UTF-8"));
        httpPost.setEntity(se);

        CloseableHttpResponse response = null;
        String result = null;
        try {
            response = HTTP_CLIENT.execute(httpPost);

            HttpEntity httpEntity = response.getEntity();
            result = EntityUtils.toString(httpEntity, Charset.forName("UTF-8"));
            JSONObject json = JSONObject.parseObject(result);
            System.out.printf(String.valueOf(json));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public void getStockBasic(){
        TushareParamsRet ret = new TushareParamsRet();
        ret.setApi_name(ApiNameEnum.STOCK_BASE.getName());
        ret.setParams("{}");
        String data = doPost(ret);
        System.out.printf(data);
    }
}
