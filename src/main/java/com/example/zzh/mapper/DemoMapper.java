package com.example.zzh.mapper;

import com.example.zzh.model.Demo;
import com.example.zzh.model.DemoTest;
import com.example.zzh.model.Score;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Author: zhao zhihong
 * @Date: 2020/1/21
 */
public interface DemoMapper {

    int add1(Demo demo);

    int add2(@Param("demo") Demo demo);

    int add3(Demo demo);

    List<Demo> get1(String name, String nickName, Integer demoType);

    List<Demo> get2(String name, String nickName, Integer demoType);

    List<Demo> get3(String name, Integer demoType, DemoTest demoTest);

    List<Demo> get4(Integer start, Integer pageSize, @Param("demo") Demo demo);

    List<Demo> testGetAllNoParam();

    Demo testGetOneNoParam();


    List<Demo> getDemosByLimit(@Param("start")Integer start,@Param("pageSize")Integer pageSize);


    Demo getOne(@Param("id")Integer id);

    int deleteDemo(@Param("id")Integer id);

    int updateById(@Param("demo") Demo demo);

}
