package com.xazhao.api.user;

import com.xazhao.api.response.UserQueryResponse;

import java.util.Map;

/**
 * @Description Created on 2024/12/05.
 * @Author Zhao.An
 */

public interface UserFacadeService {

    UserQueryResponse<Map<String, Object>> userFacadeQuery();
}
