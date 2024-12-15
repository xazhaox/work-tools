package com.xazhao.consumer;

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
public class DubboConsumerApplication {

    public static void main(String[] args) {

        SpringApplication.run(DubboConsumerApplication.class, args);
        log.info("(♥◠‿◠)ﾉﾞ  work-dubbo-consumer生产者启动成功...   ლ(´ڡ`ლ)ﾞ");
    }
}
