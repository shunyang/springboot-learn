package com.yangyang.rabbitmq.topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by chenshunyang on 2017/5/22.
 */
@Component
public class TopicAnyReceiver {

    private static Logger logger = LoggerFactory.getLogger(TopicAnyReceiver.class);

    @RabbitListener(queues = "topic.any")
    public void process(String message) {
        logger.info(String.format("TopicAnyReceiver receive topic any message: %s", message));
    }
}
