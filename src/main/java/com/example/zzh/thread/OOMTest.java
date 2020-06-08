package com.example.zzh.thread;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: zhao zhihong
 * @Date: 2019-12-26
 */
public class OOMTest {

    /**
     *  使用Executors创建线程池的方式并不推荐，因为会造成OOM（内存溢出）问题
     *  因为Executors创建的线程池中的线程数和队列的最大值均是Integer.MAX_VALUE，像是下面代码就会造成会有无数个线程占用内存
     *  最终导致内存溢出（因为for循环的代码会一直往线程池中添加任务，而线程池会持续创建线程来满足添加的任务数，直到线程数达到
     *                 Integer.MAX_VALUE，事实上达不到这个数值的时候就会溢出）
     *
     *  推荐的方式可以看HaveResult或NoResult中的代码
     */
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            executorService.execute(new ThreadRun());
        }
        executorService.shutdown();
    }

}
