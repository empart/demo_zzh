package com.example.zzh.service.impl;

import com.example.zzh.service.TestService;
import org.springframework.stereotype.Service;

/**
 * @Author: zhao zhihong
 * @Date: 2020/4/26
 */
@Service("TestServiceTwo")
public class TestServiceTwoImpl implements TestService {

    @Override
    public void testOutPut() {
        System.out.println("调用了继承类：TestServiceTwoImpl的实现方法");
    }
}
