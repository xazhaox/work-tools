package com.xazhao.cache.constant;

/**
 * 分布式锁常量
 *
 * @Description Created on 2024/12/02.
 * @Author Zhao.An
 */

public class DistributeLockConstant {

    /**
     * 默认加锁的Key
     */
    public static final String NONE_KEY = "NONE";

    public static final String DEFAULT_OWNER = "DEFAULT";

    /**
     * 默认超时时间
     */
    public static final int DEFAULT_EXPIRE_TIME = -1;

    /**
     * 默认加锁等待时间
     */
    public static final int DEFAULT_WAIT_TIME = -1;
}
