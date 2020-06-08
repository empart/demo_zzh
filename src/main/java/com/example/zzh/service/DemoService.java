package com.example.zzh.service;

import com.example.zzh.mapper.DemoMapper;
import com.example.zzh.model.Demo;
import com.example.zzh.model.DemoTest;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zhao zhihong
 * @Date: 2020/1/21
 */
@Service("demo")
public class DemoService {

    @Autowired
    private DemoMapper demoMapper;


    public void add1(Demo demo) {
        demoMapper.add1(demo);
    }

    public int add2(Demo demo) {
        return demoMapper.add2(demo);
    }

    public void add3(Demo demo) {
        demoMapper.add3(demo);
    }

    public List<Demo> get1(String name, String nickName, Integer demoType) {
        return demoMapper.get1(name, nickName, demoType);
    }

    public List<Demo> get2(String name, String nickName, Integer demoType) {
        return demoMapper.get2(name, nickName, demoType);
    }

    public List<Demo> get3(String name, Integer demoType, DemoTest demoTest) {
        return demoMapper.get3(name, demoType, demoTest);
    }

//    public List<Demo> get4(String name, Integer demoType, DemoTest demoTest) {
//        return demoMapper.get4(name,demoType,demoTest);
//    }

    public List<Demo> testGetAllNoParam() {
        return demoMapper.testGetAllNoParam();
    }

    public Demo testGetOneNoParam() {
        return demoMapper.testGetOneNoParam();
    }


    public Demo one(Integer id) {
        return demoMapper.getOne(id);
    }

    public void update(Demo demo) {
        demoMapper.updateById(demo);
    }

    public List<Demo> getDemoByPage(Integer current, Integer pageSize) {
        PageHelper.startPage(current, pageSize);
        return demoMapper.getDemos();
    }


}
