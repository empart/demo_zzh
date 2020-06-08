package com.example.zzh.test.optimized;

import org.testng.annotations.Test;

/**
 * @Author: zhao zhihong
 * @Date: 2020/5/19
 */

public class C extends D {


    @Test
    public void c() {
        System.out.println("C:" + D.getId() + "/" + D.getToken());
    }

}
