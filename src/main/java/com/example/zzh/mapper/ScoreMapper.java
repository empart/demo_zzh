package com.example.zzh.mapper;

import com.example.zzh.model.Score;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: zhao zhihong
 * @Date: 2020/3/3
 */
public interface ScoreMapper {

    List<Score> getAllNoParam();

    Score getOneNoParam();

    Score getScoreById(@Param("id") Integer id);

    int add(@Param("score")Score score);

    int updateByDemoId(@Param("demoId") Integer demoId);

    int update(@Param("id") Integer id);

    int minus(@Param("id") Integer id);

    List<Score> getList(@Param("demoId") Integer demoId, @Param("minScore") Integer minScore);
}
