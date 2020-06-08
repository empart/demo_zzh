package com.example.zzh.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author: zhao zhihong
 * @Date: 2020/5/27
 */
@Data
public class ExcelModel {

    @ExcelProperty(value = "学号")
    private Integer id;

    @ExcelProperty(value = "姓名")
    private String name;

//    @ExcelProperty(value = "出生日期")
//    private Date birthday;

    @ExcelProperty(value = "出生日期")
    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    private String birthStr;

}
