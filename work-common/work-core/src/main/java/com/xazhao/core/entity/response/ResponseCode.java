package com.xazhao.core.entity.response;

/**
 * @Description Created on 2024/12/05.
 * @Author Zhao.An
 */

public enum ResponseCode {

    /**
     * 成功
     */
    SUCCESS,

    /**
     * 重复
     */
    DUPLICATED,

    /**
     * 非法参数
     */
    ILLEGAL_ARGUMENT,

    /**
     * 系统错误
     */
    SYSTEM_ERROR,

    /**
     * 业务错误
     */
    BIZ_ERROR;
}
