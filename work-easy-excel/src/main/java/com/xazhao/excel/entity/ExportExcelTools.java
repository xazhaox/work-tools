package com.xazhao.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.alibaba.excel.enums.poi.VerticalAlignmentEnum;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description Created on 2024/11/07.
 * @Author Zhao.An
 */

@Data
public class ExportExcelTools implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ColumnWidth(value = 8)
    @ContentStyle(verticalAlignment = VerticalAlignmentEnum.CENTER, horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty(value = {"ID"}, index = 0)
    private Long id;

    /**
     * 姓名
     */
    @ColumnWidth(value = 18)
    @ContentStyle(verticalAlignment = VerticalAlignmentEnum.CENTER, horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty(value = {"Name"}, index = 1)
    private String name;

    /**
     * 地址
     */
    @ColumnWidth(value = 31)
    @ExcelProperty(value = {"Address"}, index = 2)
    private String address;

    /**
     * 年龄
     */
    @ColumnWidth(value = 11)
    @ContentStyle(verticalAlignment = VerticalAlignmentEnum.CENTER, horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty(value = {"Age"}, index = 3)
    private Long age;

    /**
     * 个人地址
     */
    @ColumnWidth(value = 43)
    @ExcelProperty(value = {"Url"}, index = 4)
    private String url;

    /**
     * 创建时间
     */
    @ColumnWidth(value = 18)
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    @ContentStyle(verticalAlignment = VerticalAlignmentEnum.CENTER, horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty(value = {"GmtCreate"}, index = 5)
    private LocalDateTime gmtCreate;

    /**
     * 更新时间
     */
    @ColumnWidth(value = 18)
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    @ContentStyle(verticalAlignment = VerticalAlignmentEnum.CENTER, horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty(value = {"GmtModified"}, index = 6)
    private LocalDateTime gmtModified;
}

