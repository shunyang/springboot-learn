package com.yangyang.rabbitmq;

import com.yangyang.rabbitmq.topic.TopicASender;
import com.yangyang.rabbitmq.topic.TopicAnySender;
import com.yangyang.rabbitmq.topic.TopicBSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by chenshunyang on 2017/5/22.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TopicTest {

    @Autowired
    private TopicASender topicASender;
    @Autowired
    private TopicBSender topicBSender;
    @Autowired
    private TopicAnySender topicAnySender;

    @Test
    public void topicA() {
        topicASender.send("topicA");
    }

    @Test
    public void topicB() {
        topicBSender.send("topicB");
    }

    @Test
    public void topicAny() {
        topicAnySender.send("topicAny");
    }
}
