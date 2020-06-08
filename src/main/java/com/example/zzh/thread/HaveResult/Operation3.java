package com.example.zzh.thread.HaveResult;

import java.util.concurrent.Callable;

/**
 * @Author: zhao zhihong
 * @Date: 2019-12-26
 */
public class Operation3 implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("业务场景三启动");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("业务场景三结束");

        return "业务场景三的返回结果";
    }
}
