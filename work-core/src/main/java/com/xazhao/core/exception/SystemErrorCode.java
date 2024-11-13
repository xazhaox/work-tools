package com.xazhao.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description Created on 2024/11/07.
 * @Author Zhao.An
 */

@Getter
@AllArgsConstructor
public enum SystemErrorCode implements ErrorCode {

    /**
     * HTTP 客户端错误
     */
    APPLICATION_CONTEXT_ERROR("APPLICATION_CONTEXT_ERROR", "applicationContext 初始化错误");

    private String code;

    private String message;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
