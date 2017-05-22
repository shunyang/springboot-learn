package com.yangyang.rabbitmq;

import com.yangyang.rabbitmq.many.MessageSender1;
import com.yangyang.rabbitmq.many.MessageSender2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by chenshunyang on 2017/5/21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ManyTest {
    @Autowired
    private MessageSender1 messageSender1;
    @Autowired
    private MessageSender2 messageSender2;

    @Test
    public void oneToMany() {
        for (int i = 0; i < 10; i++) {
            messageSender1.send(String.format("hi(%d)", i + 1));
        }
    }

    @Test
    public void manyToMany() {
        for (int i = 0; i < 10; i++) {
            messageSender1.send(String.format("he(%d)", i + 1));
            messageSender2.send(String.format("he(%d)", i + 1));
        }
    }

}
