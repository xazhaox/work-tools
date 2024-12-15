package com.xazhao.facade.config;

import com.xazhao.api.user.UserFacadeService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Dubbo配置
 *
 * @Description Created on 2024/12/05.
 * @Author Zhao.An
 */

@Configuration
public class BusinessDubboConfiguration {

    @DubboReference(version = "2024.0.1")
    private UserFacadeService userFacadeService;

    @Bean
    @ConditionalOnMissingBean(name = "userFacadeService")
    public UserFacadeService userFacadeService() {
        return userFacadeService;
    }
}
