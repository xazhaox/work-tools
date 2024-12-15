package com.xazhao.facade.service;

import com.xazhao.api.user.UserFacadeService;
import com.xazhao.api.response.UserQueryResponse;
import com.xazhao.rpc.facade.Facade;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务提供方<br/>
 *
 * <h3><br/>通过@DubboService声明一个RPC服务<h3/>
 *
 * @Description Created on 2024/12/05.
 * @Author Zhao.An
 */

@DubboService(version = "2024.0.1")
public class UserFacadeServiceImpl implements UserFacadeService {

    @Facade
    @Override
    public UserQueryResponse<Map<String, Object>> userFacadeQuery() {

        Map<String, Object> response = new HashMap<>(16);
        response.put("Name", "An.Zhao");
        response.put("Age", "18");

        UserQueryResponse<Map<String, Object>> userQueryResponse = new UserQueryResponse<>();
        userQueryResponse.setSuccess(true);
        userQueryResponse.setData(response);

        return userQueryResponse;
    }
}
