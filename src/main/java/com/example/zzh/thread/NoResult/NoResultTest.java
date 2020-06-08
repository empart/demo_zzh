package com.example.zzh.thread.NoResult;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhao zhihong
 * @Date: 2019-12-26
 */
public class NoResultTest {

    public static void main(String[] args) {
        System.out.println("主线程业务逻辑开始");

        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("线程池-%d").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 30, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(20),
                threadFactory, new ThreadPoolExecutor.AbortPolicy());

        //执行业务逻辑一
        executor.execute(new Operation1());
        //执行业务逻辑二
        executor.execute(new Operation2());
        //执行业务逻辑三
        executor.execute(new Operation3());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程业务逻辑结束");
        executor.shutdown();
    }

}
