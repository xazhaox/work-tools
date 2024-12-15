package com.xazhao.api.response;

import com.xazhao.core.entity.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description Created on 2024/12/05.
 * @Author Zhao.An
 */

@Setter
@Getter
@ToString
public class UserQueryResponse<T> extends BaseResponse {

    private static final long serialVersionUID = 1L;

    private T data;
}
