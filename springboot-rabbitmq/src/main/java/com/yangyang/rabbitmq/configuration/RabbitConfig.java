package com.yangyang.rabbitmq.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created by chenshunyang on 2017/5/18.
 */
@Configuration
public class RabbitConfig {
    @Bean
    public Queue helloQuue(){
        return new Queue("hello");
    }

    @Bean
    public Queue manyQueue() {
        return new Queue("many");
    }

    @Bean
    public Queue objectQueue() {
        return new Queue("object");
    }
}
