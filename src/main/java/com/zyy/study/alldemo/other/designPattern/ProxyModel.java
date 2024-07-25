package com.zyy.study.alldemo.other.designPattern;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyModel {
    public static void main(String[] args) {
        //JDK动态代理
        BankAccount bankAccount = new BankAccount(1000.0);
        Account account = (Account) Proxy.newProxyInstance(bankAccount.getClass().getClassLoader(), bankAccount.getClass().getInterfaces(), new AccountProxy(bankAccount));
        account.deposit(500.0);
        account.withdraw(200.0);
        System.out.println("======================================================");
        //JDK动态代理，Lambda表达式
        JavaDeveloper javaDeveloper = new JavaDeveloper();
        Developer developer = (Developer)Proxy.newProxyInstance(javaDeveloper.getClass().getClassLoader(), javaDeveloper.getClass().getInterfaces(), (object, method, params) -> {
            if ("code".equals(method.getName())) {
                System.out.println("Before code");
                method.invoke(javaDeveloper, params);
                System.out.println("After code");
            }
            if ("debug".equals(method.getName())) {
                System.out.println("Before debug");
                method.invoke(javaDeveloper, params);
                System.out.println("After debug");
            }
            return null;
        });
        developer.code();
        developer.debug();
    }
}
interface Account {
    void deposit(double amount);
    void withdraw(double amount);
}

// 实现账户接口的类
class BankAccount implements Account {
    private double balance;

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited " + amount + ", balance is now " + balance);
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Withdrew " + amount + ", balance is now " + balance);
        } else {
            System.out.println("Sorry, insufficient balance");
        }
    }
}
class AccountProxy implements InvocationHandler{
    private BankAccount bankAccount;

    public AccountProxy(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        if("deposit".equals(method.getName())){
            System.out.println("Before deposit");
            method.invoke(bankAccount, objects);
            System.out.println("After deposit");
        }
        if("withdraw".equals(method.getName())){
            System.out.println("Before withdraw");
            method.invoke(bankAccount, objects);
            System.out.println("After withdraw");
        }
        return null;
    }
}
interface Developer {

    /**
     * 编码
     */
    void code();

    /**
     * 解决问题
     */
    void debug();
}
class JavaDeveloper implements Developer {
    @Override
    public void code() {
        System.out.println("java code");
    }

    @Override
    public void debug() {
        System.out.println("java debug");
    }
}