package com.example.zzh.model;

import lombok.Data;
import org.testng.annotations.Test;

/**
 * @Author: zhao zhihong
 * @Date: 2020/3/17
 */
@Data
public class Challenge {

    private Integer recordId;

    private Integer challengerId;

    private Integer pkScore;


    public Challenge(Integer recordId, Integer challengerId, Integer pkScore) {
        this.recordId = recordId;
        this.challengerId = challengerId;
        this.pkScore = pkScore;
    }
}
