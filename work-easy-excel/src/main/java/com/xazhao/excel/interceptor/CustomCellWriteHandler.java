package com.xazhao.excel.interceptor;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.AbstractCellStyleStrategy;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;

/**
 * @Description Created on 2024/11/07.
 * @Author Zhao.An
 */

@Slf4j
public class CustomCellWriteHandler extends AbstractCellStyleStrategy {

    private static final String FONT_NAME = "Arial";

    private static final String CONTENT_FONT_NAME = "Calibri";

    private static final String TABLE_HEADER_FONT_NAME = "Calibri";

    private static final short HEADER_FONT_HEIGHT_IN_POINTS = 10;

    private static final short CONTENT_FONT_HEIGHT_IN_POINTS = 9;


    /**
     * 单元格创建后设置行高
     *
     * @param writeSheetHolder WriteSheetHolder
     * @param writeTableHolder Nullable.It is null without using table writes.
     * @param cell             Cell
     * @param head             Nullable.It is null in the case of fill data and without head.
     * @param relativeRowIndex Nullable.It is null in the case of fill data.
     * @param isHead           It will always be false when fill data.
     */
    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        Row row = cell.getRow();
        if (row != null && (row.getRowNum() == 0)) {
            // 设置第一行标题行高（以磅为单位）
            row.setHeightInPoints((short) 21);
        }
    }

    /**
     * 单元格处置后
     *
     * @param context CellWriteHandlerContext
     */
    @Override
    public void afterCellDispose(CellWriteHandlerContext context) {
        Workbook workbook = context.getWriteWorkbookHolder().getWorkbook();
        Cell cell = context.getCell();
        if (cell.getRowIndex() == 0) {
            setCellStyle(context, cell, workbook);
        }
    }

    /**
     * 设置表头格式
     *
     * @param context  CellWriteHandlerContext
     * @param cell     Cell
     * @param workbook Workbook
     */
    private void setCellStyle(CellWriteHandlerContext context, Cell cell, Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        // 字体
        font.setFontName(TABLE_HEADER_FONT_NAME);
        // 加粗
        font.setBold(true);
        // 字体大小
        font.setFontHeightInPoints(HEADER_FONT_HEIGHT_IN_POINTS);
        // 字体颜色
        font.setColor(IndexedColors.BLACK.getIndex());
        // 设置表头字体
        cellStyle.setFont(font);
        // 表头背景颜色
        cellStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        // 实心填充
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 水平居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 上下左右边框, 实线
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        // 样式写入单元格
        cell.setCellStyle(cellStyle);
        // 由于这里没有指定dataformat 最后展示的数据 格式可能会不太正确这里要把 WriteCellData的样式清空， 不然后面还有一个拦截器 FillStyleCellWriteHandler 默认会将WriteCellStyle 设置到cell里面去 会导致自己设置的不一样
        context.getFirstCellData().setWriteCellStyle(null);
    }

    /**
     * 自定义表头格式
     *
     * @return HorizontalCellStyleStrategy
     */
    public static HorizontalCellStyleStrategy setEasyExcelStyle() {
        // 表头策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        WriteFont headWriteFont = new WriteFont();
        // 字体
        headWriteFont.setFontName(TABLE_HEADER_FONT_NAME);
        // 加粗
        headWriteFont.setBold(true);
        // 标题字体大小
        headWriteFont.setFontHeightInPoints(HEADER_FONT_HEIGHT_IN_POINTS);
        // 字体颜色
        headWriteFont.setColor(IndexedColors.BLACK.getIndex());
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 表头背景颜色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        // 实心填充
        headWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 水平居中
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        headWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 内容策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 自动换行
        contentWriteCellStyle.setWrapped(false);
        // 垂直居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 上下左右边框, 实线
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        // 字体策略
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontName(CONTENT_FONT_NAME);
        // 内容字体大小
        contentWriteFont.setFontHeightInPoints(CONTENT_FONT_HEIGHT_IN_POINTS);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 使用EasyExcel策略
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }
}

