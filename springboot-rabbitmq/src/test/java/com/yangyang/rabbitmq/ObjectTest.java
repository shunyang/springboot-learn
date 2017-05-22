package com.yangyang.rabbitmq;

import com.yangyang.rabbitmq.object.ObjectSender;
import com.yangyang.rabbitmq.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by chenshunyang on 2017/5/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ObjectTest {
    private static Logger logger = LoggerFactory.getLogger(ObjectTest.class);


    @Autowired
    private ObjectSender objectSender;

    @Test
    public void test() {
        User user = new User();
        user.setId(1);
        user.setName("ConanLi");
        user.setAge(123);
        logger.info("model:"+user.toString());
        objectSender.send(user);
    }
}
