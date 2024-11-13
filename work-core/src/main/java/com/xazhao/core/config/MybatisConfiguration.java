package com.xazhao.core.config;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * MyBatis Configuration
 *
 * @Description Created on 2024/10/31.
 * @Author Zhao.An
 */

@Slf4j
@Configuration
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class MybatisConfiguration implements Provider {

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private MybatisProperties properties;

    @Bean
    public DatabaseIdProvider getDatabaseIdProvider() {
        DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        Properties properties = new Properties();
        properties.put(MYSQL, "mysql");
        properties.put(ORACLE, "oracle");
        properties.setProperty(POSTGRESQL, "postgresql");
        databaseIdProvider.setProperties(properties);
        log.info("MyBatis adaptation multiple data sources configuration is successful");
        return databaseIdProvider;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setDatabaseIdProvider(getDatabaseIdProvider());
        sessionFactory.setConfigLocation(
                new DefaultResourceLoader().getResource(properties.getConfigLocation()));
        sessionFactory.setMapperLocations(applicationContext.getResources(properties.getMapperLocations()));
        sessionFactory.setTypeAliasesPackage(properties.getTypeAliasesPackage());
        log.info("SqlSessionFactory instantiate the success");
        return sessionFactory.getObject();
    }

}
