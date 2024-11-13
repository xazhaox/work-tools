package com.xazhao.excel;

import cn.hutool.core.bean.BeanUtil;
import com.xazhao.core.config.MybatisProperties;
import com.xazhao.excel.entity.EasyExcelTools;
import com.xazhao.excel.mapper.EasyExcelToolsMapper;
import com.xazhao.excel.service.EasyExcelToolsService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @Description Created on 2024/11/07.
 * @Author Zhao.An
 */

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class EasyExcelToolsTest {

    @Resource
    private EasyExcelToolsService easyExcelToolsService;

    @Resource
    private EasyExcelToolsMapper easyExcelToolsMapper;

    @Resource
    private Environment environment;

    @Resource
    private MybatisProperties mybatisProperties;

    @Test
    public void millionExcelExportTest() {

        easyExcelToolsService.millionExcelExport(null);
    }

    @Test
    public void batchQuerySplitSheetWriteTest() {

        try {
            easyExcelToolsService.concurrentQuerySplitSheetWrite(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void concurrentQueryTest() {

        LinkedList<EasyExcelTools> easyExcelTools = easyExcelToolsService.concurrentQuery();
        System.out.println();
    }

    @Test
    public void getExcelCountTest() {

        easyExcelToolsMapper.grossAmountQuery();
    }

    @Test
    public void completeQueryTest() {

        easyExcelToolsService.completeQuery();
    }

    @Test
    public void readPropertiesTest() {

        log.info(BeanUtil.beanToMap(mybatisProperties).toString());

        String property = environment.getProperty("spring.application.name");
        log.info(property);

        String[] activeProfiles = environment.getActiveProfiles();
        log.info(Arrays.toString(activeProfiles));

        // 2147483647
        log.warn(String.valueOf(Integer.MAX_VALUE));
    }
}
