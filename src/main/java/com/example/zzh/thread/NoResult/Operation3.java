package com.example.zzh.thread.NoResult;

/**
 * @Author: zhao zhihong
 * @Date: 2019-12-26
 */
public class Operation3 implements Runnable {
    @Override
    public void run() {
        System.out.println("业务场景三启动");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("业务场景三结束");
    }
}
