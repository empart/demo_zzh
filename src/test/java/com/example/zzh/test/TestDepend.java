package com.example.zzh.test;

import com.example.zzh.ZzhApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * @Author: zhao zhihong
 * @Date: 2020/3/4
 */
@SpringBootTest(classes = ZzhApplication.class)
@Test(groups = {"test"})
public class TestDepend extends AbstractTestNGSpringContextTests {

    /**
     * 测试结果：以TestGroup和TestDepend联合测试
     *      ① 两个类如果同时有相同group名的方法，那么它们属于同一组
     *      ② dependsOnGroups = {"depend","depend2"}有该属性的方法，会等到所有属于depend组和depend2组的方法执行完才会执行
     *      ③ dependsOnMethods={"methodDepend"}有该属性的方法，会等到methodDepend方法执行完毕才会执行
     *      ④ 被依赖的方法如果执行失败（或被依赖的分组只要有一个方法执行失败），则所有依赖它的方法会被skip掉，不会再执行，
     *        但如果依赖该方法的方法如果有alwaysRun = true,即使被依赖方法失败，仍然会执行。
     *        例如：A方法有dependsOnMethods = {"B"}，如果B执行失败，那么A不会再执行，
     *             如果A方法同时有alwaysRun = true，则始终会执行。
     *  //TODO 依赖分组间的方法执行顺序
     *  //TODO  类执行影响beforeclass的执行？
     *  //TODO  分组间的方法执行顺序可能不会按照方法首字母来确定顺序
     *
     */

    @Test(groups = {"dependA"},dependsOnMethods = {"methodEepend","methodDepend"},alwaysRun = true)
    public void groupDepend(){
        System.out.println("TestDepend----运行groupDepend方法");
    }

    @Test
    public void methodDepend(){
        System.out.println("TestDepend----测试method依赖");
        int a = 1/0;
    }

    @Test
    public void methodEepend(){
        System.out.println("TestDepend----methodEepend");
//        int a = 1/0;
    }

    @Test(groups = {"dependB"},dependsOnMethods = {"groupDepend"})
    public void groupMethodDepend(){
        System.out.println("TestDepend----运行groupMethodDepend方法");
//        int a = 1/0;
    }

//    @Test(groups = {"dependB"})
//    public void test1(){
//        System.out.println("TestGroup----运行depend方法");
//    }

}
