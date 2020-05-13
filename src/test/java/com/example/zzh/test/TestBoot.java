package com.example.zzh.test;

import com.example.zzh.ZzhApplication;
import com.example.zzh.model.Demo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * @Author: zhao zhihong
 * @Date: 2020/4/24
 */
@SpringBootTest(classes = ZzhApplication.class)
public class TestBoot extends AbstractTestNGSpringContextTests {

    @Value("${zzh.code}")
    private Integer code;

    @Test
    public void test(){
        System.out.println(code);
    }

    @Test
    public void test2(){
        Demo demo = new Demo();
        demo.setName("alalala");
        System.out.println(demo.getName());
    }


}
