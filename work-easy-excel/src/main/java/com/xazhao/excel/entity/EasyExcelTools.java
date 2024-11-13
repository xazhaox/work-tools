package com.xazhao.excel.entity;

import java.time.LocalDateTime;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Description Created on 2024/11/07.
 * @Author Zhao.An
 */

@Data
@Entity
@Table(name = "easy_excel_tools")
public class EasyExcelTools implements Serializable {

    private static final long serialVersionUID = 513166290291721292L;

    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 姓名
     */
    @Column(name = "name")
    private String name;

    /**
     * 地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 年龄
     */
    @Column(name = "age")
    private Long age;

    /**
     * 个人地址
     */
    @Column(name = "url")
    private String url;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private LocalDateTime gmtCreate;

    /**
     * 更新时间
     */
    @Column(name = "gmt_modified")
    private LocalDateTime gmtModified;

}

