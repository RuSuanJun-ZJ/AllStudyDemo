package com.zyy.study.alldemo.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class MQListener {
    private static final String DirectExchange = "DirectExchange";
    private static final String DirectQueue1 = "Direct_Queue1";
    private static final String DirectQueue2 = "Direct_Queue2";
    private static final String TopicQueue1 = "Topic_Queue1";
    private static final String DeadQueue = "Dead_Queue";//用作绑定死信交换机
    private static final String DelayedQueue = "Delayed_Queue";
    private static final String BackupQueue = "Backup_Queue";//备份队列

    /*@RabbitListener(bindings = {@QueueBinding(value = @Queue(name = DirectQueue1),
            exchange = @Exchange(value = DirectExchange, type = ExchangeTypes.DIRECT), key = {"info", "warn"})})
    public void consumerDirectMessage(String message) {
        System.out.println("消费者受到队列{" + DirectQueue1 + "}消息:" + message);
    }

    @RabbitListener(bindings = {@QueueBinding(value = @Queue(name = DirectQueue2),
            exchange = @Exchange(value = DirectExchange, type = ExchangeTypes.DIRECT), key = {"error"})})
    public void consumerDirectMessage2(String message) {
        System.out.println("消费者受到队列{" + DirectQueue2 + "}消息:" + message);
    }
*/
    //上面代码可以简化为下面，只监听队列就行。【因为生产者发送消息时，指定了交换机和routingkey。所有队列确定。只监听队列就能收到消息】
    @RabbitListener(queues = DirectQueue1)
    public void consumerDirectMessage3(String message) {
        System.out.println("消费者受到队列{" + DirectQueue1 + "}消息:" + message);
    }


    @RabbitListener(queues = DirectQueue2)
    public void consumerDirectMessage4(String message) {
        System.out.println("消费者受到队列{" + DirectQueue2 + "}消息:" + message);
    }

    @RabbitListener(queues = TopicQueue1)
    public void consumerTopicMessage(Message message) {
        System.out.println("消费者受到队列{" + TopicQueue1 + "}消息:" + new String(message.getBody(), StandardCharsets.UTF_8));
    }

    @RabbitListener(queues = DeadQueue)
    public void consumerDeadMessage(Message message) {
        System.out.println("消费者受到队列{" + DeadQueue + "}消息:" + new String(message.getBody(), StandardCharsets.UTF_8) + " " +
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
    }

    @RabbitListener(queues = DelayedQueue)
    public void consumerDelayedMessage(Message message) {
        System.out.println("消费者受到队列{" + DelayedQueue + "}消息:" + new String(message.getBody(), StandardCharsets.UTF_8) + " " +
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
    }

    @RabbitListener(queues = BackupQueue)
    public void consumerBackupMessage(Message message) {
        System.out.println("消费者受到队列{" + BackupQueue + "}消息:" + new String(message.getBody()));
    }
}
