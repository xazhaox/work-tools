package com.xazhao.cache.config;

import com.xazhao.cache.aspect.DistributeLockAspect;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动装配分布式锁
 *
 * @Description Created on 2024/12/02.
 * @Author Zhao.An
 */

@Configuration
public class DistributeLockConfiguration {

    /**
     * 封装Redisson实现的分布式锁
     *
     * @param redisson RedissonClient
     * @return DistributeLockAspect
     */
    @Bean
    @ConditionalOnMissingBean
    public DistributeLockAspect distributeLockAspect(RedissonClient redisson) {
        return new DistributeLockAspect(redisson);
    }
}
