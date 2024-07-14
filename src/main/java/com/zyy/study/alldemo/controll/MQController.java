package com.zyy.study.alldemo.controll;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/sendMQ")
public class MQController {
    private static final String DirectExchange = "DirectExchange";
    private static final String DirectQueue1 = "Direct_Queue1";
    private static final String DirectQueue2 = "Direct_Queue2";
    private static final String TopicExchange = "TopicExchange";
    private static final String DelayedExchange = "DelayedExchange";
    private static final String DelayedQueue = "Delayed_Queue";
    private RabbitTemplate rabbitTemplate;
    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 设置消息发布者的消息确认及消息回退功能
     */
    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback((var1,var2,var3)->{
            String id = var1 != null ? var1.getId() : "";
            if(var2){
                System.out.println("交换机受到消息" + id);
            }else {
                System.out.println("交换机没有受到消息，因为：" + var3);
            }
        });
        //消息发送到交换机后，true：不可路由到队列则回退给发布者；false:不可路由到交换机则丢弃
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnsCallback(rm->{
            System.out.println("消息:" + new String(rm.getMessage().getBody()) + "经交换机"
                    + rm.getExchange() + "无法根据" + rm.getRoutingKey() + "路由到正确队列");
            System.out.println("消息尝试重新发送");
            new Thread(()->{
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                rabbitTemplate.convertAndSend(DirectExchange, "error", rm.getMessage());
            }).start();

        });

    }

    @GetMapping("/direct/{logLevel}/{message}")
    public void sendToDirect(@PathVariable String logLevel, @PathVariable String message) {
        rabbitTemplate.convertAndSend(DirectExchange, logLevel, message);
        System.out.println("生产者发送{" + logLevel + "}级别消息:" + message);
    }

    @GetMapping("/directTtl/{time}/{message}")
    public void sendToDirectTTL(@PathVariable String time, @PathVariable String message) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration(time);
        rabbitTemplate.convertAndSend(DirectExchange, "dead", new Message(message.getBytes(), messageProperties));
        System.out.println("生产者发送消息:" + message + " " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
    }

    @GetMapping("/topic/{topicKey}/{message}")
    public void sendToTopic(@PathVariable String topicKey, @PathVariable String message) {
        rabbitTemplate.convertAndSend(TopicExchange, topicKey, message);
        System.out.println("生产者发送{" + topicKey + "}类型消息:" + message);
    }

    /**
     * 通过延迟队列发送消息
     * @param ttl
     * @param message
     */
    @GetMapping("/delayed/{ttl}/{message}")
    public void sendToDelayed(@PathVariable String ttl, @PathVariable String message) {

        rabbitTemplate.convertAndSend(DelayedExchange, "delayedKey", message,c->{
            c.getMessageProperties().setDelay(Integer.valueOf(ttl));
            return c;
        });
        System.out.println("生产者发送延迟消息:"+ttl+"," + message + " " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
    }

    @GetMapping("confirmMessage/{message}/{id}")
    public void testConfirmMessage(@PathVariable String message,@PathVariable String id) {
        rabbitTemplate.convertAndSend(DirectExchange, "info", message, new CorrelationData(id));
    }
}
