package com.tove.market.rest.controller;

import com.example.common.Response;
import com.tove.market.api.DemoApi;
import com.tove.market.dao.major.StockBaseInfoMapper;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class DomeController implements DemoApi {
    @Resource
    private StockBaseInfoMapper stockBaseInfoMapper;

    @Override
    public Response<Boolean> addGroup(String req) {
        stockBaseInfoMapper.getAllStockInfo();
        return Response.getOk(true);
    }
}
