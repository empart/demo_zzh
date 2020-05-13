package com.example.zzh.model;

import lombok.Data;

import java.util.List;

/**
 * @Author: zhao zhihong
 * @Date: 2020/3/18
 */
@Data
public class Product {

    private Integer productId;

    private String productName;

    private Integer productStatus;

    private String imageCover;

    private String imageContent;

    private String imageIntroduction;

    private String imageEvaluation;

    private String remark;

    private String description;

    private List<Sku> productSKUList;

    private List<Integer> classIdList;

    private List<Clazz> classList;

}
