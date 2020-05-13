package com.example.zzh.service;

import com.example.zzh.mapper.ScoreMapper;
import com.example.zzh.model.Score;
import com.oracle.tools.packager.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zhao zhihong
 * @Date: 2020/3/3
 */
@Service
public class ScoreService {

    @Autowired
    private ScoreMapper scoreMapper;

    private Integer id;

    public List<Score> getAllNoParam(){
        return scoreMapper.getAllNoParam();
    }

    public Score getOneNoParam(){
        return scoreMapper.getOneNoParam();
    }

    public Score getOneScore(Integer id){
        return scoreMapper.getScoreById(id);
    }

    public int add(Score score){
        return scoreMapper.add(score);
    }

    public int updateByDemoId(Integer id) {
        return scoreMapper.updateByDemoId(id);
    }

    public int updateById(Integer id) {
        return scoreMapper.update(id);
    }

    public int minus(Integer id){
        return scoreMapper.minus(id);
    }

    public int realMinus(Integer id) throws Exception{
        int i = scoreMapper.minus(id);
        if(i == 0){
            throw new Exception("余额不足10元，无法抵扣");
        }
        return i;
    }

    public List<Score> list(Integer demoId, Integer minScore) {
        return scoreMapper.getList(demoId,minScore);
    }
}
