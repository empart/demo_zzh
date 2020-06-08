package com.example.zzh.test;

import com.example.zzh.ZzhApplication;
import com.example.zzh.model.Score;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.DataProvider;

/**
 * @Author: zhao zhihong
 * @Date: 2020/3/10
 */
@SpringBootTest(classes = ZzhApplication.class)
public class TestBase {

    /**
     *  提供给其他类使用的DataProvider，必须是静态的
     */
    @DataProvider(name = "base")
    public static Object[][] provider() {
        return new Object[][]{
                {new Score(4, 50)},
                {new Score(8, 55)}
        };
    }


}
