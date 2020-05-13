package com.example.zzh.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: zhao zhihong
 * @Date: 2020/3/18
 */
@Data
public class Sku {

    private Integer skuType;

    private Integer validityPeriod;

    private BigDecimal originalPrice;

    private BigDecimal presentPrice;

    private Integer skuId;

    private String skuDesc;

    private List<Gift> giftList;

    private List<Integer> giftIdList;

    private List<Integer> configIdList;

    private BigDecimal retailPrice;

    private Date openSale ;

    private Integer periodNum;



}
