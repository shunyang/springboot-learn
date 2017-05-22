package com.yangyang.rabbitmq.fanout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by chenshunyang on 2017/5/22.
 */
@Component
public class FanoutBReceiver {
    private static Logger logger = LoggerFactory.getLogger(FanoutSender.class);

    @RabbitListener(queues = "fanout.b")
    public void process(String message) {
        logger.info(String.format("receive fanout b message: %s", message));
    }

}

