package com.yangyang.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by chenshunyang on 2017/5/24.
 */
@Controller
@RequestMapping("/api/test")
public class TestController {
    @ResponseBody
    @RequestMapping(value = "/user", method= RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="获取user", notes="根据id获取User的接口")
    public String getUser(
            @ApiParam(required=true, name="id", value="主键id") @RequestParam(name = "id", required=true) String id){

        return "hello+"+id;
    }
}
