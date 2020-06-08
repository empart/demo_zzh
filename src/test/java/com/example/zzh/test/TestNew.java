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

    /**
     * 一个接口有不同的实现类时，使用@Qualifier来指定名字然后注入
     */
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

    /**
     *  一般判断新增方法成功不是根据影响行数来判断，因为新增一旦不成功会直接报错，不会返回0影响行数
     */
    @Test
    public void testAdd(){
        Demo demo = new Demo();
//        demo.setName("testAdd");
        demo.setName("add");
        int i = demoMapper.add2(demo);
        System.out.println("受影响的行数：" + i);
    }

    /**
     *  一般判断修改和删除方法是否成功需要根据返回的影响行数是否是0来判断，不会报错
     */
    @Test
    public void testUpdateAndDelete(){
        Demo demo = new Demo();
        demo.setId(100);
        demo.setIsDeleted(1);
        int i = demoMapper.updateById(demo);
        System.out.println("修改---受影响的行数：" + i);
        int num = demoMapper.deleteDemo(100);
        System.out.println("删除---受影响的行数：" + num);
    }


}
