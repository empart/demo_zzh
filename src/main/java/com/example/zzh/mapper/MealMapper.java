package com.example.zzh.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: zhao zhihong
 * @Date: 2020/3/18
 */
public interface MealMapper {

    List<Integer> getSkuMeals(@Param("skuId")Integer skuId);

    List<Integer> getAgentMeals(@Param("skuId")Integer skuId);

}
