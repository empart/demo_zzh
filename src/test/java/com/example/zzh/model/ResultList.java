package com.example.zzh.model;

import lombok.Data;

import java.util.List;


/**
 * @Author: zhao zhihong
 * @Date: 2020/3/16
 */
@Data
public class ResultList<T> {

    private List<T> entries;

    private Long total;

}
