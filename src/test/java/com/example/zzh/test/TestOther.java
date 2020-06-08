package com.example.zzh.test;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.DateUtils;
import com.example.zzh.ZzhApplication;
import com.example.zzh.excel.Employee;
import com.example.zzh.excel.ExcelListener;
import com.example.zzh.excel.ExcelModel;
import com.example.zzh.mapper.DemoMapper;
import com.example.zzh.mapper.ScoreMapper;
import com.example.zzh.model.Demo;
import com.example.zzh.model.Score;
import com.example.zzh.service.DemoService;
import com.example.zzh.service.ScoreService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

    @Autowired
    private DemoMapper demoMapper;

    @Autowired
    private ScoreMapper scoreMapper;

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


    @Test(groups = {"page"})
    public void testPage1(){
        PageHelper.startPage(2,10);
        List<Demo> demos = demoMapper.getDemos();
        System.out.println(demos);
        System.out.println(demos.size());

        PageHelper.offsetPage(10,10);
        List<Demo> demos2 = demoMapper.getDemos();
        System.out.println(demos2);
        System.out.println(demos2.size());
    }

    @Test(groups = {"page"})
    public void testPage2(){
        //一行PageHelper语句只对第一条查询语句生效
        PageHelper.startPage(1,7);
        List<Demo> demos = demoMapper.getDemos();
        System.out.println(demos);
        System.out.println(demos.size());

        List<Score> scores = scoreMapper.getScores();
        System.out.println(scores);
        System.out.println(scores.size());
    }

    private List<ExcelModel> data;

    @Test(groups = {"excel"})
    public void testReadExcel(){

        String fileName = "/Users/zhaozhihong/Desktop/test.xlsx";

        //listener是excel的监听解析器
        ExcelListener<ExcelModel> listener = new ExcelListener<>();
        //sheet()可以指定解析哪一个sheet，不指定默认第一个
        EasyExcel.read(fileName, ExcelModel.class, listener).sheet().doRead();

        List<ExcelModel> data = listener.getData();
        for (ExcelModel datum : data) {
            System.out.println(datum);
        }
        this.data = data;
    }

    @Test(groups = {"excel"})
    public void testWriteExcel() throws IOException {
        String fileName = "/Users/zhaozhihong/Desktop/write.xlsx";
        File file = new File(fileName);
        if(!file.exists()){
            file.createNewFile();
        }

        EasyExcel.write(file,ExcelModel.class).sheet().doWrite(data);

    }

    @Test(groups = {"excel"})
    public void testRead(){

        String fileName = "/Users/zhaozhihong/Desktop/导入员工模板0525.xlsx";

        ExcelListener<Employee> listener = new ExcelListener<>();
//        EasyExcel.read(fileName, Employee.class, listener).sheet().doRead();
        //headRowNumber可以指定哪一行开始读，默认是从第二行开始读，因为第一行是标题
        // 下面指定从第三行开始读（此时默认第二行才是标题），适合标题上面有些行被占用的场景
        EasyExcel.read(fileName, Employee.class, listener).sheet().headRowNumber(2).doRead();

        List<Employee> data = listener.getData();
        for (Employee datum : data) {
            System.out.println(datum);
        }
    }

    @Test
    public void testDate(){
        //date转string
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = format.format(date);
        System.out.println(dateStr);

        //string转date
        String s = "2020年1月1日";
        format = new SimpleDateFormat("yyyy年MM月dd日");
        try {
            Date parse = format.parse(s);
            System.out.println(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void aaa(){
        int a  =1/0;
        System.out.println(1);
    }

    @Test(dependsOnMethods = {"aaa"},alwaysRun = true)
    public void bbb(){
        System.out.println(2);
    }

}
