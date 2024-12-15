package com.xazhao.consumer.service;

import com.xazhao.api.response.UserQueryResponse;
import com.xazhao.api.user.UserFacadeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 付费消费方
 *
 * @Description Created on 2024/12/05.
 * @Author Zhao.An
 */

@Slf4j
@Service
public class UserConsumerServiceImpl implements UserConsumerService {

    @DubboReference(version = "2024.0.1")
    private UserFacadeService userFacadeService;

    @Override
    public UserQueryResponse<Map<String, Object>> userQuery() {

        return userFacadeService.userFacadeQuery();
    }
}
