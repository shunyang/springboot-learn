package com.yangyang.rabbitmq.many;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by chenshunyang on 2017/5/21.
 */
@Component
public class MessageSender1 {
    private static Logger logger = LoggerFactory.getLogger(MessageReceiver1.class);


    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(String message) {
        amqpTemplate.convertAndSend("many", message);
        logger.info(String.format("send(1) message: %s", message));
    }
}
