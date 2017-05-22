package com.yangyang.rabbitmq.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by chenshunyang on 2017/5/22.
 */
@Configuration
public class TopicRabbitConfig {
    @Bean
    public Queue topicAQueue() {
        return new Queue("topic.a");
    }

    @Bean
    public Queue topicAnyQueue() {
        return new Queue("topic.any");
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    @Bean
    public Binding bindingExchangeTopicA(Queue topicAQueue,TopicExchange topicExchange){
        return BindingBuilder.bind(topicExchange).to(topicExchange).with("topic.a");

    }

    @Bean
    public Binding bindingExchangeTopicAny(Queue topicAnyQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(topicAnyQueue).to(topicExchange).with("topic.#");
    }
}
