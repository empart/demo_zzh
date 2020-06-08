package com.example.zzh.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: zhao zhihong
 * @Date: 2020/5/27
 */
@Data
public class Employee {


    @ExcelProperty(index = 0)
    private String name;

    @ExcelProperty(index = 1)
    private String phone;

    @ExcelProperty(index = 2)
    private String role;

}
