package com.example.zzh.controller;

import com.example.zzh.model.Demo;
import com.example.zzh.model.Score;
import com.example.zzh.service.DemoService;
import com.example.zzh.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: zhao zhihong
 * @Date: 2020/3/12
 *
 *   @RestController = @Controller + @ResponseBody
 */
@RestController
public class HttpController {

    @Autowired
    private DemoService demoService;

    @Autowired
    private ScoreService scoreService;

    /**
     * demo/one?id=10
     * @param id
     * @return
     */
    @GetMapping("/demo/one")
    public Demo one(@RequestParam("id")Integer id){
        return demoService.one(id);
    }


    /**
     *      下面这种接收方式PathVariable是直接从请求路径中的{id}取到值然后放入到Integer id中
     *  /demo/one/path/10/lss
     * @param id
     * @return
     */
    @GetMapping("/demo/one/path/{id}/{name}")
    public Demo path(@PathVariable("id")Integer id,@PathVariable("name")Integer name){
        return demoService.one(id);
    }

    @GetMapping("/score/list")
    public List<Score> list(@RequestParam(required = false)Integer demoId,
                            @RequestParam(required = false)Integer minScore){
        return scoreService.list(demoId,minScore);
    }


}
