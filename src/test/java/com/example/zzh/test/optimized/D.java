package com.example.zzh.test.optimized;

import com.example.zzh.ZzhApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeSuite;

/**
 * @Author: zhao zhihong
 * @Date: 2020/6/2
 * @description：
 *
 *      编写测试类的优化版本，使用D作为父类，添加@SpringBootTest和继承AbstractTestNGSpringContextTests，
 *      子类同样可以享受效果
 */
@SpringBootTest(classes = ZzhApplication.class)
public class D extends AbstractTestNGSpringContextTests {

    private static String id;

    private static String token;

    /**
     *  此处可以请求登录接口，返回id和token供测试方法使用
     */
    @BeforeSuite
    public void init() {
        id = "loginId";
        token = "token";
    }

    public static String getId() {
        return id;
    }

    public static String getToken() {
        return token;
    }
}
