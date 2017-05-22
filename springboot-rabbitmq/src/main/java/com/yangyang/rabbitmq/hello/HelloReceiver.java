package com.yangyang.rabbitmq.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by chenshunyang on 2017/5/18.
 */
@Component
@RabbitListener(queues = "hello")
public class HelloReceiver {

    private static Logger logger = LoggerFactory.getLogger(HelloReceiver.class);


    @RabbitHandler
    public void process(String message) {
        logger.info(String.format("receive message: %s", message));
    }
}
