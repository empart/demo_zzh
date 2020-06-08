package com.example.zzh.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhao zhihong
 * @Date: 2020/5/27
 */
@Slf4j
public class ExcelListener<T> extends AnalysisEventListener<T> {

    private List<T> data = new ArrayList<>();

    public List<T> getData() {
        return data;
    }

    @Override
    public void invoke(T o, AnalysisContext analysisContext) {
        data.add(o);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("excel解析完毕");
    }
}
