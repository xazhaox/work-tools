package com.xazhao.core.concurrent;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description Created on 2024/11/07.
 * @Author Zhao.An
 */

@Slf4j
@Configuration
public class AsyncRunnableTask {

    /**
     * 线程池
     */
    @Resource
    private ThreadPoolExecutor executorService;

    /**
     * 并发执行
     *
     * @param param Runnable任务
     */
    public void invoke(Runnable param) {

        executorService.execute(param);
    }
}
