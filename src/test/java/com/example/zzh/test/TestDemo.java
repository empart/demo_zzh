package com.example.zzh.test;

import com.example.zzh.ZzhApplication;
import com.example.zzh.model.Demo;
import com.example.zzh.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

/**
 * @Author: zhao zhihong
 * @Date: 2020/3/2
 */
@SpringBootTest(classes = ZzhApplication.class)
public class TestDemo extends AbstractTestNGSpringContextTests {

    @Autowired
    private DemoService demoService;


    /**
     * 案例一： 一个suite、一个test、多个class，详见testNG.xml
     * 测试suite注解：① @BeforeSuite和@AfterSuite分别在suite标签测试开始前后执行
     * ② 注解只对包含了该注解的测试类所属的suite生效，多个测试类写了该注解，都会执行，顺序是由系统决定
     * 测试test注解：① @BeforeTest和@AfterTest分别在test标签测试开始前后执行
     * ② 注解只对包含了该注解的测试类所属的test生效，多个测试类写了该注解，都会执行，顺序是由系统决定
     * 测试class注解：① @BeforeClass和@AfterClass分别在测试类开始前后执行
     * ② 注解只对所属的测试类生效，多个测试类之间的执行顺序由xml中test标签中class标签的顺序决定
     * 测试method注解：① @BeforeMethod和@AfterMethod分别在每一个带有@Test注解方法的前后执行
     * ② 注解只对所属的测试类生效，每一个带有@Test标签的方法执行前后都会执行，没有@Test方法则不会执行
     *
     * 案例二： 一个suite、两个test、多个class，详见testNG.xml
     * ① 只会执行一个test中的suite注解方法，执行哪个由系统决定
     * ② 其余同案例一
     */

    @BeforeSuite()
    public void beforeSuite() {
        System.out.println("TestDemo----运行beforeSuite方法");
    }

    @BeforeTest()
    public void beforeTest() {
        System.out.println("TestDemo----运行beforeTest方法");
    }

    @BeforeClass()
    public void beforeClass() {
        System.out.println("TestDemo----运行beforeClass方法");
    }

    @BeforeMethod()
    public void beforeMethod() {
        System.out.println("TestDemo----运行beforeMethod方法");
    }


    @Test
    public void testGetAllNoParam() {
        System.out.println("TestDemo----运行testGetAllNoParam方法");
        List<Demo> demos = demoService.testGetAllNoParam();
        Assert.assertTrue(demos.size() > 0);
        System.out.println(demos);
    }



    @Test
    public void testGetOneNoParam() {
        System.out.println("TestDemo----运行testGetOneNoParam方法");
        Demo demo = demoService.testGetOneNoParam();
        Assert.assertNotNull(demo);
    }


    @AfterSuite
    public void afterSuite() {
        System.out.println("TestDemo----运行afterSuite方法");
    }

    @AfterTest()
    public void afterTest() {
        System.out.println("TestDemo----运行afterTest方法");
    }

    @AfterMethod()
    public void afterMethod() {
        System.out.println("TestDemo----运行afterMethod方法");
    }





}
