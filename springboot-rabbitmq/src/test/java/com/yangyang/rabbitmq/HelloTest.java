package com.yangyang.rabbitmq;

import com.yangyang.rabbitmq.hello.HelloSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by chenshunyang on 2017/5/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class HelloTest {

    @Autowired
    private HelloSender helloSender;
    @Test
    public void hello() throws Exception {
        String message ="hello"+new Date();
        helloSender.send(message);
    }
}
