package com.xazhao.core.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取mybatis配置
 *
 * @Description Created on 2024/10/31.
 * @Author Zhao.An
 */

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = MybatisProperties.PREFIX)
public class MybatisProperties {

    public static final String PREFIX = "mybatis";

    /**
     * 实体类所在的包
     */
    private String typeAliasesPackage;

    /**
     * MyBatis映射文件所在的位置，指的是编写SQL的xml位置
     */
    private String mapperLocations;

    /**
     * MyBatis全局配置文件位置
     */
    private String configLocation;

    /**
     * 全局配置
     */
    private Configuration configuration;

    /**
     * 全局yaml配置，暂不使用
     */
    @Data
    public static class Configuration {

        private boolean cacheEnabled;

        private boolean useGeneratedKeys;

        private String defaultExecutorType;

        private String logImpl;

        private boolean mapUnderscoreToCamelCase;
    }
}
