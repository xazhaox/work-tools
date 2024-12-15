package com.xazhao.consumer.service;

import com.xazhao.api.response.UserQueryResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @Description Created on 2024/12/05.
 * @Author Zhao.An
 */

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserConsumerServiceTest {

    @Resource
    private UserConsumerService userConsumerService;

    @Test
    public void userQueryTest() {

        UserQueryResponse<Map<String, Object>> mapInvokeResult = userConsumerService.userQuery();
        Map<String, Object> data = mapInvokeResult.getData();

        log.warn(data.toString());
    }
}
