package com.zyy.study.alldemo.other.designPattern;

import lombok.*;

public class Chain {
    public static void main(String[] args) {
        OrderInfo orderInfo = new OrderInfo.OrderInfoBuilder().address("111").orderId(0).price("121").build();
        /*OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId(0);*/
        HandlerOne handlerOne = new HandlerOne();
        HandlerTwo handlerTwo = new HandlerTwo();
        HandlerThree handlerThree = new HandlerThree();
        handlerOne.setNextHandler(handlerTwo);
        handlerTwo.setNextHandler(handlerThree);
        handlerOne.process(orderInfo);
        System.out.println(orderInfo.getOrderId());
    }
}

/**
 * 责任链流转的实体类
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
class OrderInfo {
    private Integer orderId;
    private String orderName;
    private String address;
    private String price;
    public void add(){
        orderId++;
    }
}

/**
 * 责任链模式顶层抽象类
 */
abstract class Handler {
    protected Handler handler;

    public void setNextHandler(Handler handler) {
        this.handler = handler;
    }

    public abstract void process(OrderInfo orderInfo);
}

class HandlerOne extends Handler {

    @Override
    public void process(OrderInfo orderInfo) {
        System.out.println("执行第一步");
        orderInfo.add();
        handler.process(orderInfo);
    }
}

class HandlerTwo extends Handler {

    @Override
    public void process(OrderInfo orderInfo) {
        System.out.println("执行第二步");
        orderInfo.add();
        handler.process(orderInfo);
    }
}
class HandlerThree extends Handler {

    @Override
    public void process(OrderInfo orderInfo) {
        System.out.println("执行第三步");
        orderInfo.add();
    }
}