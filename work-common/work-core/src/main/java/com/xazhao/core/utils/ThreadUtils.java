package com.xazhao.core.utils;

import com.xazhao.core.constant.Constant;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 线程相关工具类
 *
 * @Description Created on 2024/11/08.
 * @Author Zhao.An
 */

@Slf4j
public class ThreadUtils {

    /**
     * sleep等待，单位为毫秒
     *
     * @param milliseconds 等待时间
     */
    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            log.info("Interrupted!" + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 停止线程池
     * 先使用shutdown, 停止接收新任务并尝试完成所有已存在任务.
     * 如果超时, 则调用shutdownNow, 取消在workQueue中Pending的任务,并中断所有阻塞函数.
     * 如果仍人超時，則強制退出.
     * 另对在shutdown时线程本身被调用中断做了处理.
     *
     * @param pool ExecutorService
     */
    public static void shutdownAndAwaitTermination(ExecutorService pool) {
        if (pool != null && !pool.isShutdown()) {
            pool.shutdown();
            try {
                if (!pool.awaitTermination(Constant.THREAD_OUT_TIME, TimeUnit.SECONDS)) {
                    pool.shutdownNow();
                    if (!pool.awaitTermination(Constant.THREAD_OUT_TIME, TimeUnit.SECONDS)) {
                        log.info("Pool did not terminate");
                    }
                }
            } catch (InterruptedException ie) {
                pool.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * 打印线程异常信息
     *
     * @param runnable  Runnable
     * @param throwable Throwable
     */
    public static void printException(Runnable runnable, Throwable throwable) {
        if (throwable == null && runnable instanceof Future<?>) {
            try {
                Future<?> future = (Future<?>) runnable;
                if (future.isDone()) {
                    future.get();
                }
            } catch (CancellationException ce) {
                throwable = ce;
            } catch (ExecutionException ee) {
                throwable = ee.getCause();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
        if (throwable != null) {
            log.error(throwable.getMessage(), throwable);
        }
    }
}
