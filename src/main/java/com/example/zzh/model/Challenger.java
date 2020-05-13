package com.example.zzh.model;

import lombok.Data;

/**
 * @Author: zhao zhihong
 * @Date: 2020/2/28
 */
@Data
public class Challenger {

    private Integer challengerId;

    private String challengerName;

    private Integer totalTimes;

    private Integer isSameClass;

    private Integer pkScore;

    private String challengerHeadPic;

}
