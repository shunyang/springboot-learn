package com.yangyang.rabbitmq;

import com.yangyang.rabbitmq.fanout.FanoutSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by chenshunyang on 2017/5/22.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FanoutTest {

    private static Logger logger = LoggerFactory.getLogger(FanoutTest.class);

    @Autowired
    private FanoutSender fanoutSender;

    @Test
    public void test() {
        String message="fanout-test";
        logger.info("send fanout message:"+message);
        fanoutSender.send(message);
    }
}
