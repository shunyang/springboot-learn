package com.yangyang.springboot.controller;

import com.yangyang.springboot.domain.User;
import com.yangyang.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by chenshunyang on 2017/5/22.
 */
@Controller
@RequestMapping("/page")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user")
    public String cityForm(Model model){
        model.addAttribute("hello","hello springboot");
        return"user";
    }

    @RequestMapping("/userList")
    public String cityList(Model model){
        List<User> userList = userService.findAllUser();
        model.addAttribute("userList",userList);
        return "userList";
    }
}
