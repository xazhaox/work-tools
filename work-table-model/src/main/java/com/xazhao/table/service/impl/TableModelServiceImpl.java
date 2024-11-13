package com.xazhao.table.service.impl;

import com.xazhao.table.constant.TableFields;
import com.xazhao.table.constant.TableModelIdentifications;
import com.xazhao.table.entity.TableModel;
import com.xazhao.table.mapper.TableModelMapper;
import com.xazhao.table.service.TableModelService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description Created on 2024/10/31.
 * @Author Zhao.An
 */

@Slf4j
@Service
public class TableModelServiceImpl implements TableModelService, TableModelIdentifications, TableFields {

    public static final int SAVE_SIZE_MAX = 100;

    public static final String NULL = "null";

    private static final String SAVE_LOG_INFO = "第 {} 次插入 {} 条数据，成功！";

    @Resource
    private TableModelMapper tableModelMapper;

    /**
     * 解析表模型
     */
    @Override
    public void analyzeTableModel() {

        if (TABLE_UNIQUE_IDENTIFICATIONS.length != TARGET_TABLE_NAME.length) return;

        List<List<Map<String, Object>>> tableModels = new ArrayList<>();

        for (int index = 0; index < TARGET_TABLE_NAME.length; index++) {

            List<Map<String, Object>> tableModel = tableModelMapper.executeStoredProcedure(
                    TABLE_UNIQUE_IDENTIFICATIONS[index], DATABASE_NAME, TARGET_TABLE_NAME[index]);

            tableModels.add(tableModel);
        }

        List<TableModel> tableModelList = new ArrayList<>();

        for (List<Map<String, Object>> tableModel : tableModels) {
            for (Map<String, Object> model : tableModel) {
                TableModel tableModelVo = TableModel.builder()
                        .tableUniqueIdentification(String.valueOf(model.get(TABLE_UNIQUE_IDENTIFICATION)))
                        .columnKey(String.valueOf(model.get(IS_COLUMN_KEY)))
                        .columnName(String.valueOf(model.get(COLUMN_NAME)))
                        .dataType(String.valueOf(model.get(DATA_TYPE)))
                        .caliberSpecification(String.valueOf(model.get(CALIBER_SPECIFICATION)))
                        .build();

                String characterMaximumLength = String.valueOf(model.get(CHARACTER_MAXIMUM_LENGTH));
                if (StringUtils.isNoneBlank(characterMaximumLength) && !NULL.equals(characterMaximumLength)) {
                    tableModelVo.setCharacterMaximumLength(characterMaximumLength);
                }
                String columnComment = String.valueOf(model.get(COLUMN_COMMENT));
                if (StringUtils.isNoneBlank(columnComment) && !NULL.equals(columnComment)) {
                    tableModelVo.setColumnComment(columnComment);
                    tableModelVo.setColumnCommentDescription(columnComment);
                }

                tableModelList.add(tableModelVo);
            }
        }

        this.insertTableModel(tableModelList);
    }

    /**
     * 插入表模型到临时表
     *
     * @param tableModels 数据集
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertTableModel(List<TableModel> tableModels) {

        if (tableModels.size() > SAVE_SIZE_MAX) {
            int batchNumber = (int) Math.ceil(tableModels.size() / (double) SAVE_SIZE_MAX);
            for (int num = 0; num < batchNumber; num++) {
                int min = Math.min((num + 1) * SAVE_SIZE_MAX, tableModels.size());
                List<TableModel> tableStructure = tableModels.subList(num * SAVE_SIZE_MAX, min);
                tableModelMapper.insertTableModel(tableStructure);
                log.warn(SAVE_LOG_INFO, num + 1, tableStructure.size());
            }
        } else {
            tableModelMapper.insertTableModel(tableModels);
        }

        log.info("插入成功！");
    }

    /**
     * 插入表模型到临时表
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void clearTableModel() {

        tableModelMapper.clearTableModel();
    }
}
