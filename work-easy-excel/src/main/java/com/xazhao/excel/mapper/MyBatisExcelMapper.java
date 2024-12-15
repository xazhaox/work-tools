package com.xazhao.excel.mapper;

import com.xazhao.excel.entity.EasyExcelTools;
import com.xazhao.excel.entity.MyBatisExcel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description Created on 2024/12/12.
 * @Author Zhao.An
 */

@Mapper
public interface MyBatisExcelMapper {

    List<EasyExcelTools> pageQuery(@Param("batisExcel") MyBatisExcel myBatisExcel, @Param("url") String url);
}
