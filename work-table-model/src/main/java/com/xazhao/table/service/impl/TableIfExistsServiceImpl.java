package com.xazhao.table.service.impl;

import com.xazhao.table.constant.TableName;
import com.xazhao.table.entity.TableIfExists;
import com.xazhao.table.mapper.TableIfExistsMapper;
import com.xazhao.table.service.TableIfExistsService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Description Created on 2024/11/11.
 * @Author Zhao.An
 */

@Slf4j
@Service
public class TableIfExistsServiceImpl implements TableIfExistsService, TableName {

    public static final String DATABASE_NAME = "cpcpdb_test";

    @Resource
    private TableIfExistsMapper tableIfExistsMapper;

    @Override
    public void queryTableIfExists() {

        // tableIfExistsMapper.clearTableIfExists();

        List<TableIfExists> tableIfExists = new LinkedList<>();

        // for (String tableName : TABLE_IF_EXISTS) {
        for (String tableName : TABLE_IF_EXISTS_PROCEDURE) {

            TableIfExists tableIfExist = tableIfExistsMapper.queryTableIfExists(DATABASE_NAME, tableName);

            // tableIfExist.setTableType("Mapper");
            tableIfExist.setTableType("存储过程");

            tableIfExists.add(tableIfExist);
        }

        // 插入
        tableIfExistsMapper.insertTableIfExists(tableIfExists);
    }
}
