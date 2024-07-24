package com.zyy.study.alldemo.other.designPattern;

public class TemplateMethod {
    public static void main(String[] args) {
        templateClass templateClass = new templateClass();
        templateClass.coreMethod();
    }
}

abstract class AbstractClass {
    abstract void method1();

    abstract void method2();

    public final void coreMethod() {
        method1();
        method2();
    }
}

class templateClass extends AbstractClass {

    @Override
    public void method1() {
        System.out.println("执行方法1");
    }

    @Override
    public void method2() {
        System.out.println("执行方法2");
    }
}


