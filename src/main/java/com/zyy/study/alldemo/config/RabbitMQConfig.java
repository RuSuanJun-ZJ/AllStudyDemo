package com.zyy.study.alldemo.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    private static final String DirectExchange = "DirectExchange";
    private static final String DirectQueue1 = "Direct_Queue1";
    private static final String DirectQueue2 = "Direct_Queue2";

    @Bean
    public DirectExchange directExchange() {
        return ExchangeBuilder.directExchange(DirectExchange).build();
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

}
