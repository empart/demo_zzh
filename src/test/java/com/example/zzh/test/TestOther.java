package com.example.zzh.test;

import com.example.zzh.ZzhApplication;
import com.example.zzh.model.Demo;
import com.example.zzh.model.Score;
import com.example.zzh.service.DemoService;
import com.example.zzh.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

/**
 * @Author: zhao zhihong
 * @Date: 2020/3/11
 */
@SpringBootTest(classes = ZzhApplication.class)
public class TestOther extends AbstractTestNGSpringContextTests {

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private DemoService demoService;

    /**
     * 测试异常：如果方法没有抛出expectedExceptions中的任何一种异常，方法算作执行失败。
     */
    @Test(expectedExceptions = {NumberFormatException.class, Exception.class})
    public void testException() {
        int a = 1/0;
        String[] arr = {"a", "b"};
        System.out.println(arr[2]);

        //查商品信息
//        if(product == null){
//            throw new Exception("商品不存在");
//        }

    }

    /**
     * 测试方法执行顺序：一个测试类中的方法执行是默认按照26个英文字母顺序来的
     * 所以下列4个方法会按照testOrderA、testOrderB、testOrderC、testOrderD顺序来执行
     * 如何控制顺序：① 使用priority属性，所有方法该属性的默认值均为0（该方式不推荐使用）
     * priority是指方法被执行的权重，数值越低代表权重越高，会被优先执行
     * 所以下面的方法会按照testOrderB、testOrderD、testOrderA、testOrderC来执行
     * ② 在xml中的class标签下用methods标签的include来控制执行顺序（推荐）
     * 注：此时按照你的性格可能会问同时使用①和②会如何，priority的优先级会更高！
     */
    @Test(priority = 0,groups = {"order"})
    public void testOrderB() {
        System.out.println("执行了方法B");
    }

    @Test(priority = 1,groups = {"order"})
    public void testOrderD() {
        System.out.println("执行了方法D");
    }

    @Test(priority = 2,groups = {"order"})
    public void testOrderA() {
        System.out.println("执行了方法A");
    }

    @Test(priority = 3,groups = {"order"})
    public void testOrderC() {
        System.out.println("执行了方法C");
    }

    /**
     * 测试并发：① invocationCount代表该方法会被执行几次，threadPoolSize代表允许最多几个线程同时执行，
     * timeOut代表该方法执行的最长时间，而不是单次被执行的时间，所以下面的例子10次不会被全部执行完毕。
     * ② timeout的作用更多的是为了保证程序不会因为某个线程阻塞而一直在运行。
     */
    @Test(invocationCount = 10, threadPoolSize = 2, timeOut = 5000)
    public void testParallel() throws InterruptedException {
        System.out.println("线程" + Thread.currentThread().getName() + "开始执行");
        Thread.sleep(2000);
        System.out.println("线程" + Thread.currentThread().getName() + "结束执行");
    }

    @Test(invocationCount = 30, threadPoolSize = 5, timeOut = 20000)
    public void testParallelDatabase() {
        Random r = new Random();
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        Demo demo = new Demo();
        demo.setName("小" + letters[r.nextInt(26)]);
        demo.setDemoStatus(r.nextInt(4) + 1);
        demo.setDemoType(r.nextInt(3) + 1);
        demo.setIsDeleted(r.nextInt(2));
        demo.setNickName(letters[r.nextInt(26)]);
        int i = demoService.add2(demo);
        Assert.assertEquals(i, 1, "demo表插入失败，demo=" + demo);

        Score score = new Score();
        score.setDemoId(demo.getId());
        score.setScore(r.nextInt(101));
        int add = scoreService.add(score);
        Assert.assertEquals(add, 1, "score表插入数据失败，score=" + score);
    }

    /**
     * testUpdateParallel和testRealParallel模拟真实的并发场景
     */
    @Parameters({"id"})
    @Test(invocationCount = 10, threadPoolSize = 10, timeOut = 10000)
    public void testUpdateParallel(Integer id) {
        int minus = scoreService.minus(id);
        Assert.assertEquals(minus, 1);
    }

    @Parameters({"id"})
    @Test(invocationCount = 100, threadPoolSize = 100, timeOut = 10000)
    public void testRealParallel(Integer id) throws Exception {
        int minus = scoreService.realMinus(id);
        Assert.assertEquals(minus, 1);
    }

}
