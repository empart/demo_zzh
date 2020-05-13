package com.example.zzh.test;

import com.example.zzh.ZzhApplication;
import com.example.zzh.mapper.ScoreMapper;
import com.example.zzh.model.Score;
import com.example.zzh.service.ScoreService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

/**
 * @Author: zhao zhihong
 * @Date: 2020/3/3
 */
@SpringBootTest(classes = ZzhApplication.class)
public class TestScore extends AbstractTestNGSpringContextTests {

    @Autowired
    private ScoreService scoreService;

    @BeforeSuite
    public void beforeSuite(){
        System.out.println("TestScore----运行beforeSuite方法");
    }

    @BeforeTest
    public void beforeTest(){
        System.out.println("TestScore----运行beforeTest方法");
    }

    @BeforeClass
    public void beforeClass(){
        System.out.println("TestScore----运行beforeClass方法");
    }

    @BeforeMethod
    public void beforeMethod(){
        System.out.println("TestScore----运行beforeMethod方法");
    }

    @Test
    public void getAllNoParam(){
        System.out.println("TestScore----运行getAllNoParam方法");
        List<Score> scores = scoreService.getAllNoParam();
        Assert.assertEquals(scores.get(0).getScore().intValue(),40);
        System.out.println(scores.get(0));
    }

    @Test
    public void getOneNoParam(){
        System.out.println("TestScore----运行getOneNoParam方法");
        Score score = scoreService.getOneNoParam();
        Assert.assertNotNull(score);
    }

    @AfterSuite
    public void afterSuite(){
        System.out.println("TestScore----运行afterSuite方法");
    }

    @AfterTest
    public void afterTest(){
        System.out.println("TestScore----运行afterTest方法");
    }

    @AfterClass
    public void afterClass(){
        System.out.println("TestScore----运行afterClass方法");
    }

    @AfterMethod
    public void afterMethod(){
        System.out.println("TestScore----运行afterMethod方法");
    }



}
