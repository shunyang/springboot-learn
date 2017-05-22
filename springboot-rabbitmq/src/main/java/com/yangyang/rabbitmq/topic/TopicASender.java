package com.yangyang.rabbitmq.topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 发送者A
 * Created by chenshunyang on 2017/5/22.
 */
@Component
public class TopicASender {
    private static Logger logger = LoggerFactory.getLogger(TopicASender.class);


    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(String message){
        logger.info(String.format("send topic a message: %s", message));
        rabbitTemplate.convertAndSend("topicExchange","topic.a",message);
    }
}
