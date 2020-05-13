package com.example.zzh.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @Author: zhao zhihong
 * @Date: 2020/3/18
 */

public interface ProductMapper {

    Integer getProduct(@Param("productName")String productName);

}
