package com.yangyang.springboot.swagger.controller;

import com.yangyang.springboot.swagger.vo.UserVo;
import io.swagger.annotations.Api;
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
@Api(value = "demo", description = "demo管理", produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
@RequestMapping(value = "/api/demo")
public class DemoController {
    @ResponseBody
    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
    @ApiOperation(value="测试getUser", notes="getUser详细说明", response = UserVo.class)
    public UserVo getCount(@ApiParam(name = "userName",required = true,value = "用户名")
             @RequestParam String userName) {
        UserVo userVo = new UserVo();
         userVo.setAge(12);
         userVo.setName(userName);
         userVo.setSex("1");
        return userVo;
    }
}
