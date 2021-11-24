package com.tove.market.rest.controller;

import com.example.common.Response;
import com.tove.market.api.DemoApi;
import com.tove.market.dao.major.StockBaseInfoMapper;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class DomeController implements DemoApi {
    @Resource
    private StockBaseInfoMapper stockBaseInfoMapper;

    @Resource
    private HttpServletRequest httpServletRequest;
    @Override
    public Response<Boolean> addGroup(String req) {

        System.out.println(httpServletRequest.toString());
        stockBaseInfoMapper.getAllStockInfo();
        return Response.getOk(true);
    }
}
