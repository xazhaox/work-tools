package com.xazhao.core.exception;

/**
 * @Description Created on 2024/11/07.
 * @Author Zhao.An
 */

public interface ErrorCode {

    /**
     * 错误码
     *
     * @return 错误码
     */
    String getCode();

    /**
     * 错误信息
     *
     * @return 错误信息
     */
    String getMessage();
}
