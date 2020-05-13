package com.example.zzh.test;

import com.example.zzh.ZzhApplication;
import com.example.zzh.model.Demo;
import com.example.zzh.model.Score;
import com.example.zzh.service.ScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.*;

/**
 * @Author: zhao zhihong
 * @Date: 2020/3/2
 */
@SpringBootTest(classes = ZzhApplication.class)
@Slf4j
public class TestParam extends AbstractTestNGSpringContextTests {

    /**
     * 测试结果：
     * ① test标签中的parameter优先级要高于suite标签中的parameter,越细化的标签优先级越高，
     * 优先级由高到低：method > class > classes > test > suite
     * ② 有@DataProvider注解的方法的返回值必须为Object[][]或Iterator<Object[]>
     * 两者的区别是当有多组参数时，返回类型Iterator创建参数的时机是在方法调用时，object是一次性全部创建
     * ③ 使用了dataProvider的方法的调用次数由dataProvider的参数组数决定
     * ④ 无论是parameter还是dataProvider，使用它们的方法的参数个数、类型、顺序必须与提供方一一对应。
     * （并不是依据参数名去对应的）
     * ⑤ @optional的用法是，当没有传入参数时，被@Optional注解修饰的参数会取optional中的值
     * <p>
     * 踩坑记录：最新版本（7.1.0）的testNG使用DataProvider传递多参数时会报错
     */

    @Autowired
    private ScoreService scoreService;

    private Integer id;


    @Test(groups = "singParam")
    @Parameters({"testName"})
    public void singleParam(String s) {
        System.out.println("测试方法singleParam传入的参数为：" + s);
    }

    @Parameters({"testName", "otherName", "testId"})
    @Test(groups = "singParam")
    public void duoParam(String s, String s1, Integer id) {
        System.out.println("测试方法duoParam传入的参数为：" + s + "," + s1 + "," + id);
    }

    @DataProvider(name = "single")
    public Object[][] provider() {
//        int[] a = {1,2,3};
//        int[][] b = {{1,2,3},{10,20}};
//        Object[] o = {10};
//        Object[] o1 = {11};
//        Object[][] oo = {o,o1};
//        return oo;
        return new Object[][]{{10}, {11}};
    }

    @DataProvider(name = "duo")
    public Object[][] provider2() {
        return new Object[][]{
                {10, "param"},
                {20, "param2"},
                {30, "param3"}
        };
    }

    @DataProvider(name = "iter")
    public Iterator<Object[]> provider3() {
//        Object[] o = {"a",11};
//        Object[] o1 = {"b",12};
//        List<Object[]> list = new ArrayList<>();
//        list.add(o);
//        list.add(o1);
//        return list.iterator();
        Set<Integer> set = new HashSet<>();
        set.iterator();

        return Arrays.asList(new Object[][]{{"a", 11}, {12, "b"}}).iterator();
    }

    @Test(dataProvider = "single")
    public void singleProvider(Integer i) {
        System.out.println("测试方法singleProvider传入的参数为：" + i);
    }

    @Test(dataProvider = "duo")
    public void duoProvider(Integer i, String s) {
        System.out.println("测试方法duoProvider传入的参数为：" + i + "," + s);
    }

    @Test(dataProvider = "iter")
    public void iterProvider(String word, Integer num) {
        System.out.println("测试方法iterProvider传入的参数为：" + word + "," + num);
    }

    @Parameters({"demoId", "score"})
    @Test
    public void testAdd(Integer demoId, Integer score) {
        Score add = new Score();
        add.setDemoId(demoId);
        add.setScore(score);
        int i = scoreService.add(add);
        Demo demo = new Demo();
        demo.setName("111");
        demo.setDemoStatus(add.getId());


        log.info("新增的行数：" + i);
        this.id = add.getId();
    }

//    @Parameters({"dId"})
//    @Test(dependsOnMethods = "testAdd")
//    public void testUpdate(Integer dId){
////        int i = scoreService.updateByDemoId(demoId);
//        int i = scoreService.updateById(dId);
//        log.info("更新的行数："+i);
//    }

    @Test(dependsOnMethods = "testAdd")
    public void testUpdate() {
        int i = scoreService.updateById(id);
        log.info("更新的行数：" + i);
        Assert.assertEquals(i, 1);
    }

    @Parameters({"id"})
    @Test
    public void testOption(@Optional("1") Integer id) {
        Score oneScore = scoreService.getOneScore(id);
        System.out.println(oneScore);
        Assert.assertNotNull(oneScore);
        Assert.assertNotEquals(oneScore.getId().intValue(), 1, "使用了optional的值");
    }

    @DataProvider(name = "data")
    public Object[][] provider4() {
        return new Object[][]{
                {new Score(4, 50)},
                {new Score(8, 55)}
        };
    }

    @Test(dataProvider = "data")
    public void testAddData(Score score) {
        int i = scoreService.add(score);
        log.info("新增的行数：" + i);
        Assert.assertEquals(i, 1);
    }

    /**
     * 使用下面的方式来获取其他类的dataProvider
     * 要求其他类的dataProvider方法必须为static方法（静态方法）
     *
     * @param score
     */
    @Test(dataProvider = "base", dataProviderClass = TestBase.class)
    public void testOtherProvider(Score score) {
        System.out.println("接受到来自TestBase类的dataProvider提供的参数：" + score);
    }


    /**
     * unique测试结果：多个方法引用同一个DataProvider得到的数据彼此独立，不共享
     *
     * @return
     */
    @DataProvider(name = "unique")
    public Object[][] testUnique() {
        return new Object[][]{
                {new Score(4, 50)}
        };
    }

//    private Score score;

    @Test(groups = "unique", dataProvider = "unique")
    public void update1(Score score) {
        System.out.println(score);
        score.setScore(60);
//        this.score = score;
    }

    @Test(groups = "unique", dataProvider = "unique", dependsOnMethods = "update1")
    public void update2(Score score) {
        System.out.println(score);
    }

}
