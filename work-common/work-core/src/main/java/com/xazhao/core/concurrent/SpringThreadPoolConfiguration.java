package com.xazhao.core.concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.xazhao.core.utils.ThreadUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description Created on 2024/11/07.
 * @Author Zhao.An
 */

@Slf4j
@Configuration
public class SpringThreadPoolConfiguration implements AsyncConfigurer, ThreadPoolParameter {

    public static final String SPRING_THREAD_POOL_TASK_EXECUTOR = "executorServiceSpring";

    public static final String SCHEDULED_EXECUTOR_SERVICE = "scheduledExecutorService";

    /**
     * 创建ThreadPoolTaskExecutor线程池
     *
     * @return ThreadPoolTaskExecutor
     */

    @Primary
    @Bean(name = SPRING_THREAD_POOL_TASK_EXECUTOR)
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        // 创建线程池
        ThreadPoolTaskExecutor executorService = new ThreadPoolTaskExecutor();
        executorService.setCorePoolSize(CORE_POOL_SIZE);
        executorService.setMaxPoolSize(MAX_POOL_SIZE);
        executorService.setQueueCapacity(QUEUE_CAPACITY);
        executorService.setKeepAliveSeconds(KEEP_ALIVE_SECONDS);
        // 线程池拒绝策略
        executorService.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 线程工厂
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(SPRING_THREAD_NAME_PATTERN).build();
        executorService.setThreadFactory(threadFactory);
        // 初始化
        executorService.initialize();
        log.info("Spring ThreadPoolTaskExecutor creation is successful");
        return executorService;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) ->
                log.error("异步任务执行出现异常, message {}, method {}, params {}", throwable, method, objects);
    }

    /**
     * 执行周期性或定时任务
     */
    @Bean(name = SCHEDULED_EXECUTOR_SERVICE)
    protected ScheduledExecutorService scheduledExecutorService() {
        return new ScheduledThreadPoolExecutor(CORE_POOL_SIZE,
                new BasicThreadFactory.Builder().namingPattern(SPRING_SCHEDULE_POOL_NAME_PATTERN).daemon(true).build()) {
            @Override
            protected void afterExecute(Runnable runnable, Throwable throwable) {
                super.afterExecute(runnable, throwable);
                ThreadUtils.printException(runnable, throwable);
            }
        };
    }
}

