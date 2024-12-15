package com.xazhao.core.concurrent;

/**
 * @Description Created on 2024/11/07.
 * @Author Zhao.An
 */

public interface ThreadPoolParameter {

    /**
     * 获取CPU核数
     */
    Integer CPU_COUNT = Runtime.getRuntime().availableProcessors();

    /**
     * 核心线程池大小
     */
    Integer CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));

    /**
     * 最大可创建的线程数
     */
    Integer MAX_POOL_SIZE = CPU_COUNT * 2 + 1;

    /**
     * 队列最大长度
     */
    Integer QUEUE_CAPACITY = 1024;

    /**
     * 线程池维护线程所允许的空闲时间，这里设置5分钟，单位为秒
     */
    Integer KEEP_ALIVE_SECONDS = 300;

    /**
     * Spring 同步执行调度器的执行线程名。
     */
    String SPRING_THREAD_NAME_PATTERN = "Spring - Async - Pool - %d";

    /**
     * Java 同步执行调度器的执行线程名。
     */
    String JAVA_THREAD_NAME_PATTERN = "Java - Async - Pool - %d";

    /**
     * Java 同步执行调度器的执行线程名。
     */
    String SPRING_SCHEDULE_POOL_NAME_PATTERN = "Spring - Schedule - Pool - %d";
}
