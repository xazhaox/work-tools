package com.xazhao.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * <h3>跨域 Filter</h3>
 *
 * @Description Created on 2024/11/07.
 * @Author Zhao.An
 */

@Slf4j
@Configuration
public class CorsFilterConfiguration {

    private static final String ALLOWED_HEADERS = "*";

    private static final String ALLOWED_ORIGIN = "*";

    private static final String ALLOWED_METHOD = "*";

    private static final Long MAX_AGE = 360000L;

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedMethod(ALLOWED_METHOD);
        // 允许的来源
        corsConfig.addAllowedOriginPattern(ALLOWED_ORIGIN);
        // 允许的请求头参数
        corsConfig.addAllowedHeader(ALLOWED_HEADERS);
        // 在跨域请求的时候使用同一个session
        corsConfig.setAllowCredentials(true);
        // 这次跨域检测的有效期
        corsConfig.setMaxAge(MAX_AGE);

        // 运行访问的资源
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        CorsFilter corsFilter = new CorsFilter(source);
        log.info("Cors the configuration is successful");

        return corsFilter;
    }
}
