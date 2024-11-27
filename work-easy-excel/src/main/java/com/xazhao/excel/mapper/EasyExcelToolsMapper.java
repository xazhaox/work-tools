package com.xazhao.excel.mapper;

import com.xazhao.excel.entity.EasyExcelTools;
import org.apache.ibatis.annotations.Mapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Description Created on 2024/11/07.
 * @Author Zhao.An
 */

@Mapper
public interface EasyExcelToolsMapper {

    /**
     * 获取总数据量
     *
     * @return 总数据量
     */
    Long grossAmountQuery();

    /**
     * 查询数据
     *
     * @return 数据集
     */
    List<EasyExcelTools> selectExcel();

    /**
     * 分页查询
     *
     * @return 数据集
     */
    List<EasyExcelTools> pageHelperQueryExcel();

    /**
     * 测试Mybatis Map返回的Key是否转为小写
     *
     * @return Map
     */
    @SuppressWarnings("MybatisXMapperMethodInspection")
    List<Map<String, Object>> pageMapQuery();
}

