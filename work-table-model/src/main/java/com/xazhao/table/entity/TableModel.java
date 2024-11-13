package com.xazhao.table.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Description Created on 2024/10/31.
 * @Author Zhao.An
 */

@SuppressWarnings("all")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "table_model")
public class TableModel implements Serializable {

    private static final long serialVersionUID = 244264367315482396L;

    /**
     * 表的唯一标识
     */
    @Column(name = "table_unique_identification")
    private String tableUniqueIdentification;

    /**
     * 字段名
     */
    @Column(name = "column_name")
    private String columnName;

    /**
     * 字段中中文名
     */
    @Column(name = "column_comment")
    private String columnComment;

    /**
     * 字段类型
     */
    @Column(name = "data_type")
    private String dataType;

    /**
     * 字段长度
     */
    @Column(name = "character_maximum_length")
    private String characterMaximumLength;

    /**
     * 字段描述
     */
    @Column(name = "column_comment_description")
    private String columnCommentDescription;

    /**
     * 加工口径说明
     */
    @Column(name = "caliber_specification")
    private String caliberSpecification;

    /**
     * 是否主键
     */
    @Column(name = "column_key")
    private String columnKey;

}

