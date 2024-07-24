package com.zyy.study.alldemo.other.designPattern;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class Singleton {


    @Test
    public void t1() {
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                Singleton1.getInstance();
            }).start();
        }
    }

    @Test
    public void t2() {
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                Singleton2 instance = Singleton2.INSTANCE;
            }).start();
        }
    }

    @Test
    public void t3() {
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                Singleton3.getInstance();
            }).start();
        }
    }

    @Test
    public void t4() {
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                Singleton4.getInstance();
            }).start();
        }
    }

    @Test
    public void t5() {
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                Singleton5.getInstance();
            }).start();
        }
    }
}

/**
 * 饿汉式
 * 程序启动时加载，但是会造成资源浪费
 * 线程安全
 */
class Singleton1 {
    private static Singleton1 instance = new Singleton1();

    public Singleton1() {
        System.out.println("创建Singleton1实例");
    }

    public static Singleton1 getInstance() {
        return instance;
    }
}

/**
 * 枚举
 * 枚举线程安全且无法通过反射创建实例
 */
enum Singleton2 {
    INSTANCE;

    Singleton2() {
        System.out.println("创建Singleton2实例");
    }
}

/**
 * 懒汉式
 * 程序第一次调用时加载
 * 线程不安全
 */
class Singleton3 {
    public Singleton3() {
        System.out.println("创建Singleton3实例");
    }

    private static Singleton3 instance = null;

    public static Singleton3 getInstance() {
        if (instance == null) {
            instance = new Singleton3();
        }
        return instance;
    }
}

/**
 * 使用静态内部类创建单例
 * 线程安全
 */
class Singleton4 {
    public Singleton4() {
        System.out.println("创建Singleton4实例");
    }

    public static Singleton4 getInstance() {
        return SingletonInner.instance;
    }

    private static class SingletonInner {
        private static final Singleton4 instance = new Singleton4();
    }
}

/**
 * 双端加锁的方式创建单例
 * 线程安全
 */
class Singleton5 {
    private static volatile Singleton5 instance;
    public Singleton5() {
        System.out.println("创建Singleton5实例");
    }
    public static Singleton5 getInstance() {
        if (instance == null) {
            synchronized(Singleton5.class) {
                if (instance == null) {
                    instance = new Singleton5();
                }
            }
        }
        return instance;
    }
}

