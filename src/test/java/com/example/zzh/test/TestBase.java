package com.example.zzh.test;

import com.example.zzh.ZzhApplication;
import com.example.zzh.model.Demo;
import com.example.zzh.model.Score;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @Author: zhao zhihong
 * @Date: 2020/3/10
 */
@SpringBootTest(classes = ZzhApplication.class)
public class TestBase {

    @DataProvider(name = "base")
    public static Object[][] provider(){
//        Score score = new Score();
//        score.setScore(11);
//        score.setDemoId(11111);
        return new Object[][]{
                {new Score(4,50)},
                {new Score(8,55)}
        };
    }


}
