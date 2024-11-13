package com.xazhao.table.mapper;

import com.xazhao.table.entity.TableModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Description Created on 2024/10/31.
 * @Author Zhao.An
 */

@Mapper
public interface TableModelMapper {

    /**
     * 执行存储过程
     *
     * @param tableUniqueIdentification 表唯一标识
     * @param databaseName              数据库名称
     * @param targetTableName           表名称
     * @return 表模型
     */
    @SuppressWarnings("MybatisXMapperMethodInspection")
    List<Map<String, Object>> executeStoredProcedure(
            String tableUniqueIdentification, String databaseName, String targetTableName);

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
