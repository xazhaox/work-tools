package com.xazhao.cache.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * 将RedisCacheConfiguration这个Bean交给Spring容器管理，实现自动装配
 * <p>
 * 在resources目录下新建META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
 * <p>
 * 然后在org.springframework.boot.autoconfigure.AutoConfiguration.imports文件中加入
 * com.xazhao.config.RedisCacheConfiguration就可以将RedisCacheConfiguration这个Bean交给Spring容器管理
 *
 * @Description Created on 2024/11/27.
 * @Author Zhao.An
 */

@EnableCaching
@Configuration
public class RedisCacheConfiguration {
}
