package com.example.zzh.model;

import lombok.Data;

/**
 * @Author: zhao zhihong
 * @Date: 2020/3/16
 */
@Data
public class Result<T> {

    private boolean success;

    private Error error;

    private T data;

}
