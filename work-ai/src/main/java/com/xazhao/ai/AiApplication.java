package com.xazhao.ai;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description Created on 2024/11/15.
 * @Author Zhao.An
 */

@Slf4j
@MapperScan(basePackages = "com.xazhao.**.mapper")
@SpringBootApplication
public class AiApplication {

    public static void main(String[] args) {

        SpringApplication.run(AiApplication.class, args);
        log.info("(♥◠‿◠)ﾉﾞ  work-ai启动成功...   ლ(´ڡ`ლ)ﾞ");
    }
}
