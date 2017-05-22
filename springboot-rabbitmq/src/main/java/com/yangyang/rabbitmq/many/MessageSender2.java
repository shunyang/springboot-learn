package com.yangyang.rabbitmq.many;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by chenshunyang on 2017/5/21.
 */
@Component
public class MessageSender2 {
    private static Logger logger = LoggerFactory.getLogger(MessageReceiver1.class);


    @Autowired
    private RabbitTemplate amqpTemplate;

    public void send(String message) {
        amqpTemplate.convertAndSend("many", message);
        logger.info(String.format("send(2) message: %s", message));
    }
}
