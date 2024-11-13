package com.xazhao.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 逐行读取Excel数据
 *
 * @Description Created on 2024/11/07.
 * @Author Zhao.An
 */

@Slf4j
public class ReadExcelListener extends AnalysisEventListener<Map<Integer, String>> {

    /**
     * 读取表头
     *
     * @param headMap 表头
     * @param context AnalysisContext
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {

    }

    /**
     * 逐行解析
     *
     * @param integerStringMap 数据
     * @param analysisContext  AnalysisContext
     */
    @Override
    public void invoke(Map<Integer, String> integerStringMap, AnalysisContext analysisContext) {

    }

    /**
     * 所有数据解析完成需要执行的方法
     *
     * @param analysisContext AnalysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
