package com.example.zzh.controller;

import com.example.zzh.model.Demo;
import com.example.zzh.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: zhao zhihong
 * @Date: 2020/1/21
 *
 *      http请求测试：get、post、put、delete
 *      主要是@RequestParam和@RequestBody的使用
 */
@RestController
@Slf4j
public class DemoController {

    @Autowired
    private DemoService demoService;

    @GetMapping(value = "/get1")
    public String get1(Integer id, String name) {

        return id + name;
    }

    @GetMapping("/get2")
    public Demo get2(@RequestParam("demoId") Integer id, @RequestParam(required = false) String name) {

        Demo demo = new Demo();
        demo.setId(id);
        demo.setName(name);
        return demo;
    }

    /**
     *      get3和get4都会报错，get请求不可以这样接收数据
     */
    @GetMapping("/get3")
    public Demo get3(Demo demo) {
        return demo;
    }

    @GetMapping("/get4")
    public List<Integer> get4(@RequestParam List<Integer> demo){
        return demo;
    }

    @PostMapping("/post1")
    public String post1(Integer id, String name) {
        return id + name;
    }

    /**
     *      post2会报错，不可以用@RequestBody接收Integer这样的数据
     */
    @PostMapping("/post2")
    public String post2(@RequestBody Integer id, @RequestParam String name) {
        return id + name;
    }

    @PostMapping("/post3")
    public Demo post3(@RequestBody Demo demo, @RequestParam String name) {
        System.out.println(name);
        return demo;
    }

    /**
     *     虽然post4不会报错，但是是直接把请求过来的json串当成了字符串处理，不推荐这样写
     */
    @PostMapping("/post4")
    public String post4(@RequestBody String str) {
        return str;
    }

    @PutMapping("/put")
    public Demo put(@RequestBody Demo demo, @RequestParam Integer id) {
        System.out.println(id);
        return demo;
    }

    @DeleteMapping("/delete")
    public Demo delete(@RequestBody(required = false) Demo demo, @RequestParam(required = false) Integer id) {
        if (demo != null) {
            System.out.println(demo.getId());
        } else {
            System.out.println(id);
        }
        return demo;
    }


}
