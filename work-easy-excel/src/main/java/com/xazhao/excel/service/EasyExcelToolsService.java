package com.xazhao.excel.service;

import com.xazhao.excel.entity.EasyExcelTools;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * @Description Created on 2024/11/07.
 * @Author Zhao.An
 */

public interface EasyExcelToolsService {

    /**
     * 并发查询
     *
     * @return 数据集
     */
    LinkedList<EasyExcelTools> concurrentQuery();

    /**
     * 百万数据量导出
     *
     * @param response HttpServletResponse
     */
    void millionExcelExport(HttpServletResponse response);

    /**
     * 百万数据量导出，总数据量：1199000<br/>
     * 多线程分批查询，并发写入多个Sheet<br/>
     * 耗时： ms 左右
     *
     * @param response HttpServletResponse
     * @throws IOException IOException
     */
    void concurrentQuerySplitSheetWrite(HttpServletResponse response) throws IOException;

    /**
     * 百万数据量导出，总数据量：1000000<br/>
     * 多线程分批查询，写入一个Sheet<br/>
     * 耗时： ms 左右
     *
     * @param response HttpServletResponse
     * @throws IOException IOException
     */
    void batchQueryWriteOnce(HttpServletResponse response) throws IOException;

    /**
     * 百万数据量导出，总数据量：1000000<br/>
     * 一次查询，写入一个Sheet<br/>
     * 耗时：114735 ms 左右
     *
     * @param response HttpServletResponse
     * @throws IOException IOException
     */
    void onceQueryWriteOnce(HttpServletResponse response) throws IOException;

    /**
     * 使用PageHelper进行分页查询
     *
     * @param pageNumber 当前页码
     * @param pageSize   每页显示的数量
     * @return 数据集
     */
    List<EasyExcelTools> pageHelperQueryExcel(Integer pageNumber, Integer pageSize);

    /**
     * 全查询
     *
     * @return 数据集
     */
    List<EasyExcelTools> completeQuery();

    /**
     * 测试Mybatis Map返回的Key是否转为小写
     *
     * @return Map
     */
    List<EasyExcelTools> pageMapQuery();
}
