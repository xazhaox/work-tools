package com.xazhao.excel.controller;

import com.xazhao.excel.service.EasyExcelToolsService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

/**
 * @Description Created on 2024/11/07.
 * @Author Zhao.An
 */

@RestController
@RequestMapping("/work/excel")
public class EasyExcelToolsController {

    @Resource
    private EasyExcelToolsService easyExcelToolsService;

    /**
     * 百万数据量导出
     *
     * @param response HttpServletResponse
     */
    @PostMapping("/export")
    public void millionExcelExport(HttpServletResponse response) {

        easyExcelToolsService.millionExcelExport(response);
    }
}

