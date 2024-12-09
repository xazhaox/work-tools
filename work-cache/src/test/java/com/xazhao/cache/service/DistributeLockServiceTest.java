package com.xazhao.cache.service;

import cn.hutool.core.util.IdUtil;
import com.xazhao.cache.entity.request.PayCreateRequest;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description Created on 2024/12/02.
 * @Author Zhao.An
 */

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class DistributeLockServiceTest {

    @Resource
    private DistributeLockService distributeLockService;

    @Resource
    private ThreadPoolTaskExecutor executorService;

    @Test
    public void generatePayUrlTest() {

        // 线程数量
        int numThreads = 10000;

        for (int i = 1; i <= numThreads; i++) {
            executorService.execute(() -> {
                try {
                    // 调用generatePayUrl接口
                    PayCreateRequest payCreateRequest = new PayCreateRequest();
                    payCreateRequest.setBizNo(IdUtil.getSnowflakeNextIdStr());
                    distributeLockService.generatePayUrl(payCreateRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }


    }
}
