package com.zyy.study.alldemo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {
    private static final String DirectExchange = "DirectExchange";
    private static final String DirectQueue1 = "Direct_Queue1";
    private static final String DirectQueue2 = "Direct_Queue2";
    private static final String DirectQueue3 = "Direct_Queue3";//用作绑定死信交换机
    private static final String TopicExchange = "TopicExchange";
    private static final String TopicQueue1 = "Topic_Queue1";
    private static final String DeadExchange = "DeadExchange";
    private static final String DeadQueue = "Dead_Queue";//用作绑定死信交换机
    private static final String DelayedExchange = "DelayedExchange";
    private static final String DelayedQueue = "Delayed_Queue";
    private static final String BackupExchange = "BackupExchange";
    private static final String BackupQueue = "Backup_Queue";
    @Bean
    public DirectExchange directExchange() {
        //return ExchangeBuilder.directExchange(DirectExchange).build();
        return ExchangeBuilder.directExchange(DirectExchange).
                withArgument("alternate-exchange", BackupExchange)//配置备份交换机，当direct交换机的的消息无法路由时，发送给备份交换机
                .build();
    }

    @Bean
    public Queue directQueue1() {
        return QueueBuilder.durable(DirectQueue1).build();
    }

    @Bean
    public Queue directQueue2() {
        return QueueBuilder.durable(DirectQueue2).build();
    }

    @Bean
    public Binding directBind1(DirectExchange directExchange, @Qualifier("directQueue1") Queue directQueue1) {
        return BindingBuilder.bind(directQueue1).to(directExchange).with("info");
    }

    @Bean
    public Binding directBind2(DirectExchange directExchange, @Qualifier("directQueue1") Queue directQueue1) {
        return BindingBuilder.bind(directQueue1).to(directExchange).with("warn");
    }

    @Bean
    public Binding directBind3(DirectExchange directExchange, @Qualifier("directQueue2") Queue directQueue2) {
        return BindingBuilder.bind(directQueue2).to(directExchange).with("error");
    }
    @Bean
    public Binding directBind4(DirectExchange directExchange,  Queue directQueue3) {
        return BindingBuilder.bind(directQueue3).to(directExchange).with("dead");
    }
    @Bean
    public TopicExchange topicExchange() {
        return ExchangeBuilder.topicExchange(TopicExchange).build();
    }

    @Bean
    public Queue topicQueue() {
        return QueueBuilder.durable(TopicQueue1).build();
    }
    @Bean
    public Binding topicBind1(TopicExchange topicExchange,Queue topicQueue){
        return BindingBuilder.bind(topicQueue).to(topicExchange).with("*.zyy.*");
    }
    @Bean
    public Binding topicBind2(TopicExchange topicExchange,Queue topicQueue){
        return BindingBuilder.bind(topicQueue).to(topicExchange).with("zyy.#");
    }

    //死信交换机
    @Bean
    public DirectExchange deadExchange() {
        return ExchangeBuilder.directExchange(DeadExchange).build();
    }

    //死信队列
    @Bean
    public Queue deadQueue() {
        return QueueBuilder.durable(DeadQueue).build();
    }

    //死信交换机和死信队列绑定
    @Bean
    public Binding deadBind(DirectExchange deadExchange, Queue deadQueue) {
        return BindingBuilder.bind(deadQueue).to(deadExchange).with("deadKey");
    }

    //普通队列和私信交换机绑定，当队列内消息超时或者无法被消费，队列中的消息会发送给死信交换机
    @Bean
    public Queue directQueue3() {
        Map<String, Object> params = new HashMap<>();
        params.put("x-dead-letter-exchange", DeadExchange);
        params.put("x-dead-letter-routing-key", "deadKey");//key值必须和死信交换机和死信队列的routingKey一致
        //params.put("x-message-ttl", 10000);
        return QueueBuilder.durable(DirectQueue3)
                .withArguments(params)
                //.withArgument("x-dead-letter-routing-key", "deadKey")
                .build();
    }

    //使用RabbitMQ延迟插件，自定义延迟交换机
    @Bean
    public CustomExchange delayedExchange() {
        Map<String, Object> params = new HashMap<>();
        params.put("x-delayed-type", "direct");
        return new CustomExchange(DelayedExchange, "x-delayed-message", true, false, params);
    }

    @Bean
    public Queue delayedQueue() {
        return QueueBuilder.durable(DelayedQueue).build();
    }

    @Bean
    public Binding delayedBind(CustomExchange delayedExchange, Queue delayedQueue) {
        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with("delayedKey").noargs();
    }

    //创建备份交换机，一般设置备份交换机类型为Fanout
    @Bean
    public FanoutExchange backupExchange() {
        return ExchangeBuilder.fanoutExchange(BackupExchange).build();
    }

    @Bean
    public Queue backupQueue() {
        return QueueBuilder.durable(BackupQueue).build();
    }

    @Bean
    public Binding backupBind(FanoutExchange backupExchange, Queue backupQueue) {
        return BindingBuilder.bind(backupQueue).to(backupExchange);
    }
}
