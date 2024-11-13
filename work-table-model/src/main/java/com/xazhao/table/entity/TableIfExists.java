package com.xazhao.table.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Description Created on 2024/11/11.
 * @Author Zhao.An
 */

@SuppressWarnings("all")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "table_if_exists")
public class TableIfExists {

    private static final long serialVersionUID = -1L;

    /**
     * 表名称
     */
    @Column(name = "table_name")
    private String tableName;

    /**
     * 是否存在
     */
    @Column(name = "table_status")
    private String tableStatus;

    /**
     * 是否存在
     */
    @Column(name = "table_type")
    private String tableType;
}
