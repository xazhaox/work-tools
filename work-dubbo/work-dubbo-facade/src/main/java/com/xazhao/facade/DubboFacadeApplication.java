package com.xazhao.facade;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description Created on 2024/12/05.
 * @Author Zhao.An
 */

@Slf4j
@MapperScan(basePackages = "com.xazhao.**.mapper")
@SpringBootApplication
@EnableDubbo
public class DubboFacadeApplication {

    public static void main(String[] args) {

        SpringApplication.run(DubboFacadeApplication.class, args);
        log.info("(♥◠‿◠)ﾉﾞ  work-dubbo-facade生产者启动成功...   ლ(´ڡ`ლ)ﾞ");
    }
}
