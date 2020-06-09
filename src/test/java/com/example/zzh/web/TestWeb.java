package com.example.zzh.web;

import com.example.zzh.test.optimized.D;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import java.io.File;

/**
 * @Author: zhao zhihong
 * @Date: 2020/6/9
 */
public class TestWeb extends D {

    @Test
    public void test(){
        File file = new File("driver/chromedriver");
        String absolutePath = file.getAbsolutePath();
        System.setProperty("webdriver.chrome.driver","driver/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get("http://www.baidu.com");
    }

}
