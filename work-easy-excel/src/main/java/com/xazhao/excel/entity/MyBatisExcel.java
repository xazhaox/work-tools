package com.xazhao.excel.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description Created on 2024/12/12.
 * @Author Zhao.An
 */

@Data
public class MyBatisExcel implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> ages;

    private String name;
}
