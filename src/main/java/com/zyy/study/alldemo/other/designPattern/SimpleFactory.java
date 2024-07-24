package com.zyy.study.alldemo.other.designPattern;

public class SimpleFactory {
    public static void main(String[] args) {
        Product phone = ProductFactory.createProduct("phone");
        Product computer = ProductFactory.createProduct("computer");
        phone.createProduct();
        computer.createProduct();
    }
}

interface Product {
    public void createProduct();
}

class Phone implements Product {

    @Override
    public void createProduct() {
        System.out.println("生产一部手机");
    }
}

class Computer implements Product {

    @Override
    public void createProduct() {
        System.out.println("生产一台电脑");
    }
}
class ProductFactory{
    public static Product createProduct(String productType) {
        if ("phone".equals(productType)) {
            return new Phone();
        } else if ("computer".equals(productType)) {
            return new Computer();
        }
        return null;
    }
}
