package com.zyy.study.alldemo.controll;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sendMQ")
public class MQController {
    private static final String DirectExchange = "DirectExchange";
    private static final String DirectQueue1 = "Direct_Queue1";
    private static final String DirectQueue2 = "Direct_Queue2";
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/direct/{logLevel}/{message}")
    public void sendToDirect(@PathVariable String logLevel, @PathVariable String message) {
        rabbitTemplate.convertAndSend(DirectExchange, logLevel, message);
        System.out.println("生产者发送{" + logLevel + "}级别消息:" + message);
    }
}
