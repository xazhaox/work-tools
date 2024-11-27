package com.xazhao.cache.config;

import com.xazhao.cache.core.RedisCache;
import com.xazhao.cache.core.RedisCacheStr;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 将CacheConfiguration这个Bean交给Spring容器管理，实现自动装配
 *
 * @Description Created on 2024/11/27.
 * @Author Zhao.An
 */

@Slf4j
@Configuration
public class CacheConfiguration {

    /**
     * 封装RedisTemplate工具类
     *
     * @return RedisCache
     */
    @Bean
    public RedisCache redisCache() {
        RedisCache redisCache = new RedisCache();
        log.info("RedisTemplate creation is successful");
        return redisCache;
    }

    /**
     * 封装RedisTemplate工具类
     *
     * @return StringRedisTemplate
     */
    @Bean
    public RedisCacheStr redisCacheStr() {
        RedisCacheStr redisCacheStr = new RedisCacheStr();
        log.info("StringRedisTemplate creation is successful");
        return redisCacheStr;
    }
}
