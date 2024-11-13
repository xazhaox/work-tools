package com.xazhao.excel.interceptor;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * 设置冻结表头区域
 *
 * @Description Created on 2024/11/07.
 * @Author Zhao.An
 */

@Slf4j
public class CustomSheetHandler implements SheetWriteHandler {

    /**
     * 冻结的列数
     */
    private int colSplit;

    /**
     * 冻结的行数
     */
    private int rowSplit;

    /**
     * 右窗格中可见的左列
     */
    private int leftmostColumn;


    private int topRow;

    public CustomSheetHandler(int colSplit, int rowSplit, int leftmostColumn, int topRow) {
        this.colSplit = colSplit;
        this.rowSplit = rowSplit;
        this.leftmostColumn = leftmostColumn;
        this.topRow = topRow;
    }

    /**
     * 默认冻结表头
     */
    public CustomSheetHandler() {
        this.colSplit = 0;
        this.rowSplit = 1;
        this.leftmostColumn = 0;
        this.topRow = 1;
    }

    /**
     * 设置冻结表头区域
     *
     * @param writeWorkbookHolder
     * @param writeSheetHolder
     */
    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        Sheet sheet = writeSheetHolder.getSheet();
        sheet.createFreezePane(colSplit, rowSplit, leftmostColumn, topRow);
    }
}
