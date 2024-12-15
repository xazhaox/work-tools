package com.xazhao.consumer.service;

import com.xazhao.api.response.UserQueryResponse;

import java.util.Map;

/**
 * @Description Created on 2024/12/05.
 * @Author Zhao.An
 */

public interface UserConsumerService {

    UserQueryResponse<Map<String, Object>> userQuery();
}
