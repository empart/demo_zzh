package com.example.zzh.test.optimized;

import org.testng.annotations.Test;

/**
 * @Author: zhao zhihong
 * @Date: 2020/5/19
 */
public class B extends D {


    @Test
    public void b() {
        System.out.println("B:" + D.getId() + "/" + D.getToken());
    }


}
