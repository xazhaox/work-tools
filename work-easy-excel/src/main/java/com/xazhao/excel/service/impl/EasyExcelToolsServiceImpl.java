package com.xazhao.excel.service.impl;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.github.pagehelper.PageHelper;
import com.xazhao.core.constant.Charsets;
import com.xazhao.core.exception.ServiceErrorCode;
import com.xazhao.core.exception.ServiceException;
import com.xazhao.excel.entity.EasyExcelTools;
import com.xazhao.excel.entity.ExportExcelTools;
import com.xazhao.excel.interceptor.CustomCellWriteHandler;
import com.xazhao.excel.interceptor.CustomSheetHandler;
import com.xazhao.excel.mapper.EasyExcelToolsMapper;
import com.xazhao.excel.service.EasyExcelToolsService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description Created on 2024/11/07.
 * @Author Zhao.An
 */

@Slf4j
@Service
public class EasyExcelToolsServiceImpl implements EasyExcelToolsService {

    @Resource
    private EasyExcelToolsMapper easyExcelToolsMapper;

    @Resource
    private ThreadPoolTaskExecutor executorService;

    /**
     * 分批查询百万条数据页数，10 ~ 20页
     */
    public static final int FOR_PAGE_NUMBER_MAX = 20;

    /**
     * 并发查询百万条数据页数
     */
    public static final int PAGE_NUMBER_MAX = 20;

    /**
     * 并发查询1000000条数据，加载到LinkedList
     *
     * @return 数据集
     */
    @Override
    public LinkedList<EasyExcelTools> concurrentQuery() {
        Instant startTime = Instant.now();

        // 获取总数据量
        Long grossAmount = easyExcelToolsMapper.grossAmountQuery();
        // 计算每页数量
        int pageSize = (int) Math.ceil(grossAmount / (double) PAGE_NUMBER_MAX);

        // 汇总任务，预先分配空间
        List<CompletableFuture<List<EasyExcelTools>>> completableFutures = new ArrayList<>(PAGE_NUMBER_MAX);

        // 设置每个CompletableFuture的任务，每个任务进行不同的分页查询
        for (int pageNum = 1; pageNum <= PAGE_NUMBER_MAX; pageNum++) {
            int pageIndex = pageNum;
            CompletableFuture<List<EasyExcelTools>> futureExcel = CompletableFuture.supplyAsync(()
                    // 分页查询，CompletableFuture使用自定义线程池
                    -> this.pageHelperQueryExcel(pageIndex, pageSize), executorService);
            completableFutures.add(futureExcel);
        }

        // 等待所有的CompletableFuture任务执行完毕，这里需要将completableFutures转为数组，数组的长度设置为0，是因为toArray()方法在这种情况下会根据列表completableFutures中的实际元素个数自动创建一个新的、大小合适的CompletableFuture类型的数组来容纳所有元素
        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0])).join();

        // 汇总结果，由于数据量大使用LinkedList
        LinkedList<EasyExcelTools> easyExcelTools = new LinkedList<>();

        // 获取每个CompletableFuture任务执行的结果
        for (CompletableFuture<List<EasyExcelTools>> completableFuture : completableFutures) {
            try {
                // 获取结果，超时时间2s
                List<EasyExcelTools> futureExcels = completableFuture.get(2, TimeUnit.SECONDS);
                easyExcelTools.addAll(futureExcels);

            } catch (InterruptedException | ExecutionException | TimeoutException e) {

                log.info("Get CompletableFuture result failed. ");
                throw new ServiceException(ServiceErrorCode.GET_COMPLETABLE_FUTURE_RESULT_FAILED);
            }
        }

        log.info("数据查询完毕. 耗时：{} ms，总数据量：{}", Duration.between(startTime, Instant.now()).toMillis(), easyExcelTools.size());
        return easyExcelTools;
    }

    /**
     * 百万数据量导出
     *
     * @param response HttpServletResponse
     */
    @Override
    public void millionExcelExport(HttpServletResponse response) {
        Instant startTime = Instant.now();

        try {
            // 导出Excel
            this.concurrentQuerySplitSheetWrite(response);

        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException(ServiceErrorCode.EXCEL_EXPORT_FAILED);
        }

        log.info("EasyExcelTools结果数据导出Excel耗时：{} ms", Duration.between(startTime, Instant.now()).toMillis());
    }

    /**
     * <h3>多线程分批查询，并发写入多个Sheet，总数据量：1000000<h3/>
     *
     * @param response HttpServletResponse
     */
    @Override
    public void concurrentQuerySplitSheetWrite(HttpServletResponse response) throws IOException {

    }

    /**
     * <h3>分批查询，写入一个Sheet，总数据量：1000000<h3/>
     *
     * @param response HttpServletResponse
     */
    @Override
    public void batchQueryWriteOnce(HttpServletResponse response) throws IOException {

        // 获取总数据量
        Long grossAmount = easyExcelToolsMapper.grossAmountQuery();
        log.warn("Excel count：{}", grossAmount);
        // 每页数量
        int pageSize = (int) Math.ceil(grossAmount / (double) FOR_PAGE_NUMBER_MAX);

        // 创建Excel，格式为xlsx
        ExcelWriter excelWriter = EasyExcelFactory.write(response.getOutputStream(), ExportExcelTools.class)
                .excelType(ExcelTypeEnum.XLSX).autoCloseStream(Boolean.TRUE).build();

        // 创建Sheet
        WriteSheet writeSheet = EasyExcelFactory.writerSheet(1, "分批查询_写入一个Sheet")
                // 使用自定义EasyExcel策略
                .registerWriteHandler(CustomCellWriteHandler.setEasyExcelStyle())
                .registerWriteHandler(new CustomCellWriteHandler())
                // 设置冻结表头区域
                .registerWriteHandler(new CustomSheetHandler())
                .build();

        for (int pageNum = 1; pageNum <= FOR_PAGE_NUMBER_MAX; pageNum++) {
            // 分页工具
            List<EasyExcelTools> easyExcelTools = this.pageHelperQueryExcel(pageNum, pageSize);
            // 写入Excel
            excelWriter.write(easyExcelTools, writeSheet);
        }

        // 设置response
        this.setHeaderCharacter(response,
                URLEncoder.encode("分批查询_写入一个Sheet" + ExcelTypeEnum.XLSX.getValue(), StandardCharsets.UTF_8));
        excelWriter.finish();
    }

    /**
     * <h3>一次查询，写入一个Sheet，总数据量：1000000<h3/>
     *
     * @param response HttpServletResponse
     */
    @Override
    public void onceQueryWriteOnce(HttpServletResponse response) throws IOException {

        List<EasyExcelTools> easyExcelTools = this.completeQuery();

        // 创建Excel，格式为xlsx
        ExcelWriter excelWriter = EasyExcelFactory.write(response.getOutputStream(), ExportExcelTools.class)
                .excelType(ExcelTypeEnum.XLSX).autoCloseStream(Boolean.TRUE).build();

        // 创建Sheet
        WriteSheet writeSheet = EasyExcelFactory.writerSheet(1, "一次查询_写入一个Sheet")
                // 使用自定义EasyExcel策略
                .registerWriteHandler(CustomCellWriteHandler.setEasyExcelStyle())
                .registerWriteHandler(new CustomCellWriteHandler())
                // 设置冻结表头区域
                .registerWriteHandler(new CustomSheetHandler())
                .build();

        // 写入Excel
        excelWriter.write(easyExcelTools, writeSheet);

        // 设置response
        this.setHeaderCharacter(response,
                URLEncoder.encode("一次查询_写入一个Sheet" + ExcelTypeEnum.XLSX.getValue(), StandardCharsets.UTF_8));
        excelWriter.finish();
    }

    /**
     * 使用PageHelper进行分页查询
     *
     * @param pageNumber 当前页码
     * @param pageSize   每页显示的数量
     * @return 数据集
     */
    @Override
    public List<EasyExcelTools> pageHelperQueryExcel(Integer pageNumber, Integer pageSize) {
        // 分页工具
        PageHelper.startPage(pageNumber, pageSize);
        List<EasyExcelTools> easyExcelTools = easyExcelToolsMapper.pageHelperQueryExcel();
        PageHelper.clearPage();
        return easyExcelTools;
    }

    /**
     * 全查询
     */
    @Override
    public List<EasyExcelTools> completeQuery() {
        Instant startTime = Instant.now();
        List<EasyExcelTools> easyExcelTools = easyExcelToolsMapper.selectExcel();
        log.info("全查询耗时：{} ms", Duration.between(startTime, Instant.now()).toMillis());
        return easyExcelTools;
    }

    /**
     * 设置Header参数
     *
     * @param response 响应体
     * @param fileName 文件名称
     */
    public void setHeaderCharacter(HttpServletResponse response, String fileName) {
        response.reset();
        response.setCharacterEncoding(Charsets.UTF_8);
        response.setHeader("content-type", "application/octet-stream");
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }
}
