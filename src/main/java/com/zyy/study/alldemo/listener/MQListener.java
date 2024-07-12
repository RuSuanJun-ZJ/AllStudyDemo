package com.zyy.study.alldemo.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MQListener {
    private static final String DirectExchange = "DirectExchange";
    private static final String DirectQueue1 = "Direct_Queue1";
    private static final String DirectQueue2 = "Direct_Queue2";

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
}
