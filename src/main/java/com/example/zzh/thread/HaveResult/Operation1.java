package com.example.zzh.thread.HaveResult;

import java.util.concurrent.Callable;

/**
 * @Author: zhao zhihong
 * @Date: 2019-12-26
 */
public class Operation1 implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("业务场景一启动");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("业务场景一结束");

        return "业务场景一的返回结果";
    }
}
