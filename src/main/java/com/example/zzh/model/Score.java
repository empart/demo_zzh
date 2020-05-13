package com.example.zzh.model;

import lombok.Data;

/**
 * @Author: zhao zhihong
 * @Date: 2020/3/3
 */
@Data
public class Score {

    private Integer id;

    private Integer demoId;

    private Integer score;

    public Score() {
    }

    public Score(Integer demoId, Integer score) {
        this.demoId = demoId;
        this.score = score;
    }
}
