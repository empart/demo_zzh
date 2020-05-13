package com.example.zzh.test;

import com.example.zzh.ZzhApplication;
import com.example.zzh.mapper.DemoMapper;
import com.example.zzh.model.Demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.List;

/**
 * mysql分页测试类
 *
 * @Author: zhao zhihong
 * @Date: 2020/3/13
 */
@SpringBootTest(classes = ZzhApplication.class)
public class TestPage extends AbstractTestNGSpringContextTests {

    @Autowired
    private DemoMapper demoMapper;

    @Test
    public void testLimit() {
        int current = 1;
        int pageSize = 5;

        int start = (current - 1) * pageSize;
        List<Demo> demos = demoMapper.getDemosByLimit(start, pageSize);
        System.out.println("第" + current + "页返回的数据长度：" + demos.size());
        System.out.println("第" + current + "页返回的数据：" + demos);

        current = 2;
        pageSize = 5;

        start = (current - 1) * pageSize;
        demos = demoMapper.getDemosByLimit(start, pageSize);
        System.out.println("第" + current + "页返回的数据长度：" + demos.size());
        System.out.println("第" + current + "页返回的数据：" + demos);

        current = 3;
        pageSize = 5;

        start = (current - 1) * pageSize;
        demos = demoMapper.getDemosByLimit(start, pageSize);
        System.out.println("第" + current + "页返回的数据长度：" + demos.size());
        System.out.println("第" + current + "页返回的数据：" + demos);

    }

}
