package com.example.zzh.thread.lockTest;

/**
 * @Author: zhao zhihong
 * @Date: 2019-11-14
 */

public class PersonTest {


    /**
     *  通过一个person对象来创建多个线程，测试锁的用法
     *  常见的锁分为添加synchronized关键字或者使用lock对象，详见Person类
     */
    public static void main(String[] args) {
        Person p = new Person();
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(p,"线程"+(i+1));
            t.start();
        }
    }

}
