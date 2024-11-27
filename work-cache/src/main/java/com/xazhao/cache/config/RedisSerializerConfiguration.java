package com.xazhao.cache.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 将RedisSerializerConfiguration交给Spring的容器管理，将自定义的RedisTemplate注入到容器
 * <p>
 * AutoConfigureBefore该注解表示在RedisAutoConfiguration创建redisTemplate之前创建我自定义的redisTemplate
 *
 * @Description Created on 2024/11/27.
 * @Author Zhao.An
 */

@Configuration
@AutoConfigureBefore(value = RedisAutoConfiguration.class)
public class RedisSerializerConfiguration {

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisFactory) {
        // 创建RedisTemplate对象
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        // 设置连接工厂
        template.setConnectionFactory(redisFactory);
        // 创建JSON序列化工具
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(RedisSerializer.string());
        // Hash的key也采用StringRedisSerializer的序列化方式
        template.setHashKeySerializer(RedisSerializer.string());
        // 设置Value的序列化
        template.setHashValueSerializer(serializer);
        template.setValueSerializer(serializer);
        // 确保RedisTemplate的序列化设置应用正确
        template.afterPropertiesSet();
        return template;
    }
}
