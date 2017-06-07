package com.yangyang.springboot.custom.controller;

import com.yangyang.springboot.example.autoconfigure.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chenshunyang on 2017/5/23.
 */
@RestController
public class IndexController {
    @Autowired
    private ExampleService exampleService;

    @GetMapping("/input")
    public String input(String word){
        return exampleService.wrap(word);
    }

}
