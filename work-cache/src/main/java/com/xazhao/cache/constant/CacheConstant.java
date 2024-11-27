package com.xazhao.cache.constant;

/**
 * @Description Created on 2024/11/27.
 * @Author Zhao.An
 */

public class CacheConstant {

    /**
     * 缓存key分隔符
     */
    public static final String CACHE_KEY_SEPARATOR = ":";

    /**
     * 用户相关
     */
    public static final String USER = "user";

    /**
     * 用户token
     */
    public static final String USER_TOKENS = USER + CACHE_KEY_SEPARATOR + "user_tokens";

    /**
     * 保存用户token的hash Key，目录为user/
     */
    public static final String LOGIN_HASH_TABLE_KEY = USER_TOKENS + CACHE_KEY_SEPARATOR + "login_hash_table";

    /**
     * 将Jwt有效负载设置为由指定名称值对填充的JSON声明实例，该Key对应的是用户信息
     */
    public static final String AUTHORITIES_KEY = "Auth";

    /**
     * 用户ID
     */
    public static final String USER_ID = "id";
}
