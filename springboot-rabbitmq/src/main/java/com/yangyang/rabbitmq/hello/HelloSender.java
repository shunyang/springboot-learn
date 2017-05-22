package com.yangyang.rabbitmq.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by chenshunyang on 2017/5/18.
 */
@Component
public class HelloSender {
    private static Logger logger = LoggerFactory.getLogger(HelloSender.class);


    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(String message) {
        logger.info(String.format("send message: %s", message));
        this.rabbitTemplate.convertAndSend("hello", message);
    }
}
