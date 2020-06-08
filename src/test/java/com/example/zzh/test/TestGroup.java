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
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @Author: zhao zhihong
 * @Date: 2020/3/2
 */
@SpringBootTest(classes = ZzhApplication.class)
@Test(groups = {"test"})
public class TestGroup extends AbstractTestNGSpringContextTests {

    @Autowired
    private DemoService demoService;
    @Autowired
    private ScoreService scoreService;

    /**
     * 测试结果：
     *  ① 测试类中定义的group名字可以在xml中使用，但xml中用define标签定义的group名字不可以在测试类中使用
     *
     *  ② 测试类的类名上的@Test中的group代表包含此类的所有方法，每个方法上的@Test中的group代表所有包含此group名字的方法
     *    例如：本类的group为test，所以本类方法都属于test组，其中有的方法是属于all组，有的属于demo组等等，
     *         对于方法testGetAllNoParam来说，它属于test、all、demo三个组。
     *
     *  ③ @BeforeGroups和@AfterGroups只会对注解中指定的group执行时生效
     *   （下面的该方法指的是被@BeforeGroups和@AfterGroups修饰的方法）
     *    例1：通过xml执行，带有@BeforeGroups(groups = {"all"})注解的方法只有当xml中run标签里的组包含all时才会执行，
     *         如果一个方法同时属于all组和score组，当run标签里是score组时，依然不会执行该方法。
     *    例2：通过方法直接执行，如果执行带有@Test(groups = {"all","demo"})的方法testGetAllNoParam，因为属于all分组，
     *        此时依然会执行带有@BeforeGroups(groups = {"all"})的方法beforeGroup。
     *
     *  ④ 方法testGetAllNoParam同时属于all分组和demo分组，如果在xml中run标签下同时包含all和demo分组，
     *      testGetAllNoParam只会执行一次。
     *     如果在xml中run标签下包含all分组，同时不包含demo分组，testGetAllNoParam不会被执行。
     *
     *  ⑤ BeforeClass和AfterClass注解修饰的方法如果不属于run标签中的分组，则不会运行。
     *      例如：run标签中的分组为all，但是本类的beforeClass方法不属于这个分组，所以没有运行。
     *
     *  注: Assert.assertEquals的用法有一点需要注意，该方法只支持8种基本类型或object类型对比，
     *      所以包装器类型要先转换成基本数据类型然后再去对比。
     *      例：Integer a = 10
     *          int b = 10
     *      如果调用Assert.assertEquals（a，b），系统会不知道该调用Assert.assertEquals（int，int）
     *      还是Assert.assertEquals（object，object），把a转成int后系统就可以区分了。
     *
     */

    @BeforeClass(groups = {"score"})
    public void beforeClass(){
        Integer a  =10;
        int b = 10;
        Assert.assertEquals(a.intValue(),b);

        System.out.println("TestGroup----运行beforeClass方法");
    }


    @BeforeGroups(groups = {"all"})
    public void beforeGroup(){
        System.out.println("TestGroup----运行beforeGroup方法");
    }

    @Test(groups = {"all","demo"})
    public void testGetAllNoParam(){
        System.out.println("TestGroup----运行testGetAllNoParam方法");
        List<Demo> demos = demoService.testGetAllNoParam();
        Assert.assertTrue(demos.size() == 0,"断言失败");
    }

    @Test(groups = {"one","demo"})
    public void testGetOneNoParam(){
        System.out.println("TestGroup----运行testGetOneNoParam方法");
        Demo demo = demoService.testGetOneNoParam();
        Assert.assertNotNull(demo);
    }

    @Test(groups = {"score","all"})
    public void getAllNoParam(){
        System.out.println("TestGroup----运行getAllNoParam方法");
        List<Score> scores = scoreService.getAllNoParam();
        Assert.assertEquals(scores.get(0).getScore().intValue(),50);
    }

    @Test(groups = {"score","one"})
    public void getOneNoParam(){
        System.out.println("TestGroup----运行getOneNoParam方法");
        Score score = scoreService.getOneNoParam();
        Assert.assertNotNull(score);
    }

    @AfterGroups(groups = {"all"})
    public void afterGroup(){
        System.out.println("TestGroup----运行afterGroup方法");
    }


    @Test(dependsOnGroups = {"dependB","dependA"},alwaysRun = true)
    public void depend(){
        System.out.println("TestGroup----测试group依赖");
    }

    @Test(groups = {"dependA"})
    public void test1(){
        System.out.println("TestGroup----运行depend方法");
    }

    @Test(groups = {"dependB"})
    public void groupMethodDepend(){
        System.out.println("TestGroup----运行groupMethodDepend方法");
//        int a = 1/0;
    }

}
