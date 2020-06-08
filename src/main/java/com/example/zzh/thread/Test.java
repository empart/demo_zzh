package com.example.zzh.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author: zhao zhihong
 * @Date: 2019-12-25
 */
public class Test {

    public static void main(String[] args) throws Exception {
        //execute和submit都是提交任务到线程池，再由线程池分配线程去执行
        // 所以execute和submit并不是真正的去执行任务
        //TODO 具体的代码内容请到HaveResult包和NoResult包查看，下面只是简单示例
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        //第一种方式使用execute提交任务，无法接受到run方法的返回值
//        executorService.execute(new ThreadRun());
//        executorService.execute(new ThreadRun());
//        executorService.execute(new ThreadRun());

        //第二种方式使用submit提交任务，可以接收run方法的返回值（通过Future对象的get方法）
        //由于get方法会造成主线程阻塞，所以要一起调用所有线程的get方法，而不是分开调用
        //下面是分开调用，会导致每个线程获取到结果后才会去执行下个线程，这样就达不到多线程并发执行的目的
        Future<?> submit = executorService.submit(new ThreadRun());
//        submit.get();
        Future<?> submit1 = executorService.submit(new ThreadRun());
//        submit1.get();
        Future<?> submit2 = executorService.submit(new ThreadRun());
//        submit2.get();
        //推荐使用下面这样同时获取返回结果
        submit.get();
        submit1.get();
        submit2.get();
        System.out.println("测试测试");
        executorService.shutdown();
    }

}
