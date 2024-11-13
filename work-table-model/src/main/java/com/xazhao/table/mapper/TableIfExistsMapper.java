package com.xazhao.table.mapper;

import com.xazhao.table.entity.TableIfExists;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description Created on 2024/11/11.
 * @Author Zhao.An
 */

@Mapper
public interface TableIfExistsMapper {

    /**
     * 查询表是否存在
     *
     * @param databaseName 库名
     * @param tableName    表名
     * @return 是否存在
     */
    TableIfExists queryTableIfExists(String databaseName, String tableName);

    /**
     * 插入
     *
     * @param tableIfExists 数据
     */
    void insertTableIfExists(List<TableIfExists> tableIfExists);

    /**
     * 清空表
     */
    void clearTableIfExists();
}
