package com.example.zzh.test.optimized;

import org.testng.annotations.Test;

/**
 * @Author: zhao zhihong
 * @Date: 2020/5/19
 */
public class A extends D {


    @Test
    public void a() {
        System.out.println("A:" + D.getId() + "/" + D.getToken());
    }
}
