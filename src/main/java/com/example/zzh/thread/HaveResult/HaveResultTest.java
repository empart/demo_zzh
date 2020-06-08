package com.example.zzh.thread.HaveResult;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @Author: zhao zhihong
 * @Date: 2019-12-26
 */
public class HaveResultTest {

    public static void main(String[] args) throws Exception{
        System.out.println("主线程业务逻辑开始");
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("线程池-%d").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 30, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(20),
                threadFactory, new ThreadPoolExecutor.AbortPolicy());
        //执行业务逻辑一
        Future<String> future1 = executor.submit(new Operation1());
        //执行业务逻辑二
        Future<Integer> future2 = executor.submit(new Operation2());
        //执行业务逻辑三
        Future<String> future3 = executor.submit(new Operation3());

        String result1 = future1.get();
        Integer result2 = future2.get();
        String result3 = future3.get();

        System.out.println(new StringBuilder().append("业务返回的结果为：").append(result1).append("+")
                            .append(result2).append("+").append(result3).append("。").toString());
        System.out.println("主线程业务逻辑结束");
        executor.shutdown();

    }

}
