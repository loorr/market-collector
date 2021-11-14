package com.tove.market.api;

import com.example.common.Response;
import com.tove.market.api.common.Constant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api(value = "测试接口")
@FeignClient(name = Constant.APP_REST_NAME)
public interface DemoApi {
    @ApiOperation("用户建群聊")
    @PostMapping(value = "/group/add-group", produces = MediaType.APPLICATION_JSON_VALUE)
    Response<Boolean> addGroup(@RequestBody @Validated String req);
}
