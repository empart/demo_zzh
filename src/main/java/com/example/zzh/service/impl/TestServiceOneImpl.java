package com.example.zzh.service.impl;

import com.example.zzh.service.TestService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @Author: zhao zhihong
 * @Date: 2020/4/26
 */
@Service("TestServiceOne")
public class TestServiceOneImpl implements TestService {

    @Override
    public void testOutPut() {

        System.out.println("调用了继承类：TestServiceOneImpl的实现方法");
    }
}
