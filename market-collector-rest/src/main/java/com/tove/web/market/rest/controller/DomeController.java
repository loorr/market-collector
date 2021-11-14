package com.tove.web.market.rest.controller;

import com.example.common.Response;
import com.tove.web.market.api.DemoApi;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DomeController implements DemoApi {
    @Override
    public Response<Boolean> addGroup(String req) {
        return Response.getOk(true);
    }
}
