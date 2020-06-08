package com.example.zzh.thread.lockTest;

/**
 * @Author: zhao zhihong
 * @Date: 2019-11-14
 */
public class Person implements Runnable{

    private static int id;

    private String name;

    private int count;

    public int getId() {
        return id;
    }

    public static void setId(int id) {
        Person.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person:" + "name=" + name +",id=" + id ;
    }


//    @Override
//    public void run() {
//        synchronized (this){
//            try {
//                Thread.sleep(2000);
//                System.out.println(Thread.currentThread().getName()+"======111111");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    @Override
    public synchronized void run() {

        try {
//            Thread.sleep(2000);
            count++;
            if(count%2 == 0){
                System.out.println(Thread.currentThread().getName()+"开始等待");
                wait();
            }else{
                System.out.println(Thread.currentThread().getName()+"执行唤醒");
                notifyAll();
            }
            System.out.println(Thread.currentThread().getName()+"======111111");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
//    @Override
//    public void run() {
////        Lock lock = new ReentrantLock();
//        lock.lock();
//        try {
//            Thread.sleep(2000);
//            System.out.println(Thread.currentThread().getName()+"======111111");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }finally {
//            lock.unlock();
//        }
//
//    }
}
