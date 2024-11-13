package com.xazhao.table.service;

import com.xazhao.table.entity.TableModel;

import java.util.List;

/**
 * @Description Created on 2024/10/31.
 * @Author Zhao.An
 */

public interface TableModelService {

    /**
     * 解析表模型
     */
    void analyzeTableModel();

    /**
     * 插入表模型到临时表
     *
     * @param tableModels 数据集
     */
    void insertTableModel(List<TableModel> tableModels);

    /**
     * 清空表模型
     */
    void clearTableModel();
}
