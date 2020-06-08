package com.example.zzh.thread.HaveResult;

import java.util.concurrent.Callable;

/**
 * @Author: zhao zhihong
 * @Date: 2019-12-26
 */
public class Operation2 implements Callable<Integer> {


    @Override
    public Integer call() throws Exception {
        System.out.println("业务场景二启动");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("业务场景二结束");

        return 21111;
    }

    public void aaa(){

    }
}
