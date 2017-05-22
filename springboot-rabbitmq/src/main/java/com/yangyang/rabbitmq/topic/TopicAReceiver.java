package com.yangyang.rabbitmq.topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by chenshunyang on 2017/5/22.
 */
@Component
public class TopicAReceiver {
    private static Logger logger = LoggerFactory.getLogger(TopicAReceiver.class);

    @RabbitListener(queues = "topic.a")
    public void process(String message) {
        logger.info(String.format("TopicAReceiver receive topic a message: %s", message));
    }

}
