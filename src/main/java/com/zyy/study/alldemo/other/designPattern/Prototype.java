package com.zyy.study.alldemo.other.designPattern;

public class Prototype {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            PrototypeClass instance = PrototypeClass.getInstance();
            System.out.println(instance instanceof PrototypeClass);
        }
    }
}
class PrototypeClass implements Cloneable{
    private static PrototypeClass instance = new PrototypeClass();

    public static PrototypeClass getInstance() {
        try {
            return (PrototypeClass)instance.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
