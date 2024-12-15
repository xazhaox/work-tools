package com.xazhao.excel.service.impl;

import com.xazhao.excel.entity.EasyExcelTools;
import com.xazhao.excel.entity.MyBatisExcel;
import com.xazhao.excel.mapper.MyBatisExcelMapper;
import com.xazhao.excel.service.MyBatisExcelService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @Description Created on 2024/12/12.
 * @Author Zhao.An
 */

@Slf4j
@Service
public class MyBatisExcelServiceImpl implements MyBatisExcelService {

    @Resource
    private MyBatisExcelMapper batisExcelMapper;

    @Override
    public void pageQuery() {

        String url = "http://image.lanr.biz/ToolsHomeDecoration";
        MyBatisExcel myBatisExcel = new MyBatisExcel();
        List<String> ages = new LinkedList<>();
        ages.add("25");
        ages.add("986");
        ages.add("388");
        myBatisExcel.setAges(ages);
        List<EasyExcelTools> easyExcelTools = batisExcelMapper.pageQuery(myBatisExcel, url);
        log.info(easyExcelTools.toString());
    }
}
