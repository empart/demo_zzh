package com.example.zzh.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: zhao zhihong
 * @Date: 2020/3/18
 */
public interface ChannelMapper {

    List<Integer> checkChannleSku(@Param("skuId")Integer skuId);

}
