package com.xazhao.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description Created on 2024/11/13.
 * @Author Zhao.An
 */

@Slf4j
@MapperScan(basePackages = "com.xazhao.**.mapper")
@SpringBootApplication
public class ConcurrentProgramApplication {

    public static void main(String[] args) {

        SpringApplication.run(ConcurrentProgramApplication.class, args);
        log.info("(♥◠‿◠)ﾉﾞ  work-concurrent-program启动成功...   ლ(´ڡ`ლ)ﾞ");
    }
}
