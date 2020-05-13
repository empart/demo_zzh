package com.example.zzh.test;

import com.example.zzh.ZzhApplication;
import com.example.zzh.mapper.DemoMapper;
import com.example.zzh.model.Demo;
import com.example.zzh.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * @Author: zhao zhihong
 * @Date: 2020/4/26
 */
@SpringBootTest(classes = ZzhApplication.class)
public class TestNew extends AbstractTestNGSpringContextTests {

    @Autowired
    @Qualifier("TestServiceOne")
    private TestService testServiceOne;

    @Autowired
    @Qualifier("TestServiceTwo")
    private TestService testServiceTwo;




    @Autowired
    private DemoMapper demoMapper;

    /**
     * 测试接口的不同实现类
     */
    @Test
    public void testImpl() {
        testServiceOne.testOutPut();
        testServiceTwo.testOutPut();
    }

    @Test
    public void testAdd(){
        Demo demo = new Demo();
//        demo.setName("testAdd");
        demo.setName("add");
        int i = demoMapper.add2(demo);
        System.out.println("受影响的行数：" + i);
    }

    @Test
    public void testUpdateAndDelete(){
        Demo demo = new Demo();
        demo.setId(100);
        demo.setIsDeleted(1);
        int i = demoMapper.updateById(demo);
        System.out.println("修改---受影响的行数：" + i);
        int num = demoMapper.deleteDemo(100);
        System.out.println("删除---受影响的行数：" + i);
    }


}
