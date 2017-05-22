package com.yangyang.rabbitmq.fanout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by chenshunyang on 2017/5/22.
 */
@Component
public class FanoutSender {

    private static Logger logger = LoggerFactory.getLogger(FanoutSender.class);


    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(String message) {
        rabbitTemplate.convertAndSend("fanoutExchange", "", message);
        logger.info(String.format("send fanout message: %s", message));
    }
}
