package com.example.zzh.thread;

/**
 * @Author: zhao zhihong
 * @Date: 2019-12-25
 */
public class ThreadRun implements Runnable{


    @Override
    public void run(){

        System.out.println(Thread.currentThread().getName() + "正在运行");
        try {
            Thread.sleep(10000);
            System.out.println(Thread.currentThread().getName() + "运行完毕");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
