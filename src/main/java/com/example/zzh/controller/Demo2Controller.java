package com.example.zzh.controller;

import com.example.zzh.model.Demo;
import com.example.zzh.model.DemoTest;
import com.example.zzh.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: zhao zhihong
 * @Date: 2020/2/25
 */
@RestController
public class Demo2Controller {

    @Autowired
    private DemoService demoService;

    @PostMapping("/add1")
    public void add1(@RequestBody Demo demo) {
        demoService.add1(demo);
    }

    @PostMapping("/add2")
    public void add2(@RequestBody Demo demo) {
        demoService.add2(demo);
    }

    @PostMapping("/add3")
    public void add3(@RequestBody Demo demo) {
        demoService.add3(demo);
    }

    @GetMapping("/getDemo1")
    public List<Demo> get1(@RequestParam String name,
                           @RequestParam(required = false) String nickName,
                           @RequestParam Integer demoType){
        return demoService.get1(name,nickName,demoType);
    }

    @GetMapping("/getDemo2")
    public List<Demo> get2(@RequestParam String name,
                           @RequestParam(required = false) String nickName,
                           @RequestParam Integer demoType){
        return demoService.get2(name,nickName,demoType);
    }

    @GetMapping("/getDemo3")
    public List<Demo> get3(@RequestParam String name,
                           @RequestParam Integer demoType,
                           DemoTest demoTest){
        return demoService.get3(name,demoType,demoTest);
    }

//    @GetMapping("/getDemo4")
//    public List<Demo> get4(@RequestParam String name,
//                           @RequestParam Integer demoType,
//                           DemoTest demoTest){
//        return demoService.get4(name,demoType,demoTest);
//    }

}
