package com.example.zzh.model;

import lombok.Data;

import java.util.Date;

/**
 * @Author: zhao zhihong
 * @Date: 2020/3/18
 */
@Data
public class Clazz {

    private Integer classId;

    private String className;

    private Integer textbookId;

    private String textbookName;

    private String openingTime;

    private Date classStartTime;

    private Integer undueStudentNum;

    private Integer allStudentNum;

    private Date classEndTime;

}
