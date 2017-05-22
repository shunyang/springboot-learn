# SpringBoot-RabbitMQ（消息队列）

> 此文择取于 [Lewe](http://www.jianshu.com/u/6de0b7e9137c) 的 [RabbitMQ基础概念详细介绍](http://www.jianshu.com/p/b26336fd1e90) 和 [极乐君](http://www.jianshu.com/u/55af8d0de729) 的 [Spring Boot系列(八)：RabbitMQ详解](http://www.jianshu.com/p/26b233ca7a4e)

## RabbitMQ简介

AMQP，即Advanced Message Queuing Protocol，高级消息队列协议，是应用层协议的一个开放标准，为面向消息的中间件设计。消息中间件主要用于组件之间的解耦，消息的发送者无需知道消息使用者的存在，反之亦然。AMQP的主要特征是面向消息、队列、路由（包括点对点和发布/订阅）、可靠性、安全。

RabbitMQ是一个开源的AMQP实现，服务器端用Erlang语言编写，支持多种客户端，如：Python、Ruby、.NET、Java、JMS、C、PHP、ActionScript、XMPP、STOMP等，支持AJAX。用于在分布式系统中存储转发消息，在易用性、扩展性、高可用性等方面表现不俗。

## 相关概念

### Queue

Queue（队列）是RabbitMQ的内部对象，用于存储消息。

![queue](queue.png)

RabbitMQ中的消息都只能存储在Queue中，生产者（下图中的P）生产消息并最终投递到Queue中，消费者（下图中的C）可以从Queue中获取消息并消费。

![queue-one](queue-one.png)

多个消费者可以订阅同一个Queue，这时Queue中的消息会被平均分摊给多个消费者进行处理，而不是每个消费者都收到所有的消息并处理。

![queue-two](queue-two.png)

### Exchange

通常我们谈到队列服务，会有三个概念：发消息者、队列、收消息者，RabbitMQ在这个基本概念之上，多做了一层抽象，在发消息者和队列之间，加入了交换器（Exchange）。这样发消息者和队列就没有直接联系，转而变成发消息者把消息给交换器，交换器根据调度策略再把消息再给队列。

![exchange](exchange.png)

RabbitMQ常用的Exchange Type有fanout、direct、topic、headers四种。

#### *fanout*

把所有发送到该Exchange的消息路由到所有与它绑定的Queue中。

![exchange-fanout](exchange-fanout.png)

- 生产者（P）发送到Exchange（X）的所有消息都会路由到图中的两个Queue，并最终被两个消费者（C1与C2）消费。

#### *direct*

把消息路由到bindingKey与routingKey完全匹配的Queue中。

![exchange-direct](exchange-direct.png)

- routingKey=”error”发送消息，则会同时路由到Queue1（amqp.gen-S9b…）和Queue2（amqp.gen-Agl…）
- routingKey=”info”或routingKey=”warning”发送消息，则只会路由到Queue2
- 以其他routingKey发送消息，则不会路由到这两个Queue中

#### *topic*

把消息路由到bindingKey与routingKey模糊匹配的Queue中，匹配规则如下：

- routingKey为一个句点号“.”分隔的字符串（被句点号“.”分隔开的每一段独立的字符串称为一个单词）
- bindingKey与routingKey一样也是句点号“.”分隔的字符串
- bindingKey中可以存在两种特殊字符“*”与“#”，用于做模糊匹配，其中“*”用于匹配一个单词，“#”用于匹配多个单词（可以是零个）

![exchange-topic](exchange-topic.png)

- routingKey=”quick.orange.rabbit”发送信息，则会同时路由到Q1与Q2
- routingKey=”lazy.orange.fox”发送信息，则只会路由到Q1
- routingKey=”lazy.brown.fox”发送消息，则只会路由到Q2
- routingKey=”lazy.pink.rabbit”发送消息，则只会路由到Q2（只会投递给Q2一次，虽然这个routingKey与Q2的两个bindingKey都匹配）
- routingKey=”quick.brown.fox”、routingKey=”orange”、routingKey=”quick.orange.male.rabbit”发送消息，则会被丢弃，它们并没有匹配任何bindingKey

#### *headers*

headers类型的Exchange不依赖于routingKey与bindingKey的匹配规则来路由消息，而是根据发送的消息内容中的headers属性进行匹配。在绑定Queue与Exchange时指定一组键值对；当消息发送到Exchange时，RabbitMQ会取到该消息的headers（也是一个键值对的形式），对比其中的键值对是否完全匹配Queue与Exchange绑定时指定的键值对；如果完全匹配则消息会路由到该Queue，否则不会路由到该Queue。

### RPC

MQ本身是基于异步的消息处理，前面的示例中所有的生产者（P）将消息发送到RabbitMQ后不会知道消费者（C）处理成功或者失败（甚至连有没有消费者来处理这条消息都不知道）。但实际的应用场景中，我们很可能需要一些同步处理，需要同步等待服务端将我的消息处理完成后再进行下一步处理。这相当于RPC（Remote Procedure Call，远程过程调用）。在RabbitMQ中也支持RPC。

![rpc](rpc.png)

RabbitMQ中实现RPC的机制是：

客户端发送请求（消息）时，在消息的属性（MessageProperties，在AMQP协议中定义了14中properties，这些属性会随着消息一起发送）中设置两个值replyTo（一个Queue名称，用于告诉服务器处理完成后将通知我的消息发送到这个Queue中）和correlationId（此次请求的标识号，服务器处理完成后需要将此属性返还，客户端将根据这个id了解哪条请求被成功执行了或执行失败）

- 服务器端收到消息并处理
- 服务器端处理完消息后，将生成一条应答消息到replyTo指定的Queue，同时带上correlationId属性
- 客户端之前已订阅replyTo指定的Queue，从中收到服务器的应答消息后，根据其中的correlationId属性分析哪条请求被执行了，根据执行结果进行后续业务处理

## 安装RabbitMQ

此处不进行详细说明。

默认访问路径：`http://localhost:15672`

默认账号/密码：`guest/guest`

## SpringBoot集成RabbitMQ

### HelloWorld

#### *1. pom.xml添加依赖*

```java
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

#### *2. application.properties配置*

```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

#### *3. RabbitConfig队列配置*

```java
@Configuration
public class RabbitConfig {

    @Bean
    public Queue helloQueue() {
        return new Queue("hello");
    }

}
```

#### *4. HelloSender发送者*

```java
@Component
public class HelloSender {

    private static Logger logger = Logger.getLogger(HelloSender.class);

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(String message) {
        rabbitTemplate.convertAndSend("hello", message);
        logger.info(String.format("send message: %s", message));
    }

}
```

#### *5. HelloReceiver接收者*

```java
@Component
@RabbitListener(queues = "hello")
public class HelloReceiver {

    private static Logger logger = Logger.getLogger(HelloReceiver.class);

    @RabbitHandler
    public void process(String message) {
        logger.info(String.format("receive message: %s", message));
    }

}
```

#### *6. HelloTest测试*

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloTest {

    @Autowired
    private HelloSender helloSender;

    @Test
    public void test() {
        helloSender.send("hello world");
    }

}
```

#### *7. 测试结果*

```text
send message: hello world
receive message: hello world
```

### 多对多使用

默认情况下，M个发送者，N个接收者，消息会均匀的发送到N个接收者中。

#### *1. ManyTest测试*

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class ManyTest {

    @Autowired
    private MessageSender1 messageSender1;
    @Autowired
    private MessageSender2 messageSender2;

    @Test
    public void manyToMany() {
        for (int i = 0; i < 10; i++) {
            messageSender1.send(String.format("hi(%d)", i + 1));
            messageSender2.send(String.format("hi(%d)", i + 1));
        }
    }

}
```

#### *2. 测试结果*

接收者1、2均匀接收到信息。

```text
receive(1) message: hi(1)
receive(2) message: hi(1)
receive(1) message: hi(3)
receive(2) message: hi(3)
receive(1) message: hi(4)
receive(2) message: hi(4)
receive(1) message: hi(2)
receive(2) message: hi(2)
receive(1) message: hi(5)
receive(2) message: hi(5)
receive(1) message: hi(6)
receive(2) message: hi(6)
receive(1) message: hi(7)
receive(2) message: hi(7)
receive(1) message: hi(8)
receive(2) message: hi(8)
receive(1) message: hi(9)
receive(2) message: hi(9)
receive(1) message: hi(10)
receive(2) message: hi(10)
```

### 对象的支持

#### *1. 发送者与接收者*

```java
// 发送者
public void send(User user) {
    rabbitTemplate.convertAndSend("object", user);
    logger.info(String.format("send object: %s", user));
}

...

// 接收者
@RabbitListener(queues = "object")
public void process(User user) {
    logger.info(String.format("receive object: %s", user));
}
```

#### *2. 测试结果*

SpringBoot完美的支持对象的发送和接收，不需要格外的配置。

```java
send object: User{id=1, name=ConanLi}
receive object: User{id=1, name=ConanLi}
```

### FanoutExchange

Fanout 就是我们熟悉的广播模式或者订阅模式，给Fanout交换机发送消息，绑定了这个交换机的所有队列都收到这个消息。

#### *1. FanoutRabbitConfig队列配置*

```java
@Configuration
public class FanoutRabbitConfig {

    @Bean
    public Queue fanoutAQueue() {
        return new Queue("fanout.a");
    }

    @Bean
    public Queue fanoutBQueue() {
        return new Queue("fanout.b");
    }

    @Bean
    public Queue fanoutCQueue() {
        return new Queue("fanout.c");
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    public Binding bindingExchangeFanoutA(Queue fanoutAQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutAQueue).to(fanoutExchange);
    }

    @Bean
    public Binding bindingExchangeFanoutB(Queue fanoutBQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutBQueue).to(fanoutExchange);
    }

    @Bean
    public Binding bindingExchangeFanoutC(Queue fanoutCQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutCQueue).to(fanoutExchange);
    }

}
```

#### *2. 发送者与接收者*

```java
// 发送者
public void send(String message) {
    rabbitTemplate.convertAndSend("fanoutExchange", "", message);
    logger.info(String.format("send fanout message: %s", message));
}

...

// 接收者A
@RabbitListener(queues = "fanout.a")
public void process(String message) {
    logger.info(String.format("receive fanout a message: %s", message));
}

...

// 接收者B
@RabbitListener(queues = "fanout.b")
public void process(String message) {
    logger.info(String.format("receive fanout b message: %s", message));
}

...

// 接收者C
@RabbitListener(queues = "fanout.c")
public void process(String message) {
    logger.info(String.format("receive fanout c message: %s", message));
}
```

#### *3. 测试结果*

接收者A、B、C均接收到信息。

```java
send fanout message: email
receive fanout b message: email
receive fanout a message: email
receive fanout c message: email
```

### TopicExchange

Topic是RabbitMQ中最灵活的一种方式，可以根据routing_Key自由的绑定不同的队列。

#### *1. TopicRabbitConfig队列配置*

```java
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
    public Binding bindingExchangeTopicA(Queue topicAQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(topicAQueue).to(topicExchange).with("topic.a");
    }

    @Bean
    public Binding bindingExchangeTopicAny(Queue topicAnyQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(topicAnyQueue).to(topicExchange).with("topic.#");
    }

}
```

#### *2. 发送者与接收者*

```java
// 发送者A
public void send(String message) {
    rabbitTemplate.convertAndSend("topicExchange", "topic.a", message);
    logger.info(String.format("send topic a message: %s", message));
}

...

// 发送者B
public void send(String message) {
    rabbitTemplate.convertAndSend("topicExchange", "topic.b", message);
    logger.info(String.format("send topic b message: %s", message));
}

...

// 发送者Any
public void send(String message) {
    rabbitTemplate.convertAndSend("topicExchange", "topic.any", message);
    logger.info(String.format("send topic any message: %s", message));
}

...

// 接收者A
@RabbitListener(queues = "topic.a")
public void process(String message) {
    logger.info(String.format("receive topic a message: %s", message));
}

...

// 接收者Any
@RabbitListener(queues = "topic.any")
public void process(String message) {
    logger.info(String.format("receive topic any message: %s", message));
}
```

#### *3. 测试结果*

接收到A接收到发送者A的信息。

接收者Any接收到发送者A、发送者B、发送者Any的信息。

```java
send topic a message: tag
receive topic a message: tag
receive topic any message: tag

...

send topic b message: tag
receive topic any message: tag

...

send topic any message: tag
receive topic any message: tag
```

*PS：本文使用的是spring-boot-1.4.4.RELEASE、rabbit-3.6.6*