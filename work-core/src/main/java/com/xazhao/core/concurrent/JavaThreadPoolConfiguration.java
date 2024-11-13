package com.xazhao.core.concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description Created on 2024/11/07.
 * @Author Zhao.An
 */

@Slf4j
@Configuration
public class JavaThreadPoolConfiguration implements ThreadPoolParameter {

    /**
     * 创建ThreadPoolExecutor线程池
     *
     * @return ThreadPoolExecutor
     */
    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        // 线程工厂，设置线程名称
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(JAVA_THREAD_NAME_PATTERN).build();
        // 阻塞队列
        ArrayBlockingQueue<Runnable> arrayBlockingQueue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
        log.info("Java ThreadPoolExecutor creation is successful");
        // 创建线程池
        return new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                arrayBlockingQueue, threadFactory, new ThreadPoolExecutor.AbortPolicy());
    }
}
