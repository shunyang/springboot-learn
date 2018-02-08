package com.yangyang.springboot.actuator.endpoint;

import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 创建端点配置类，并注册我们的端点 ServerTimeEndpoint
 * @author chenshunyang
 * @create 2018-02-08 13:41
 **/
@Configuration
public class EndpointConfig {

    @Bean
    public static Endpoint<Map<String,Object>> serverTime(){
        return new ServerTimeEndpoint();
    }
}
