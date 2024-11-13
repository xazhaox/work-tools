package com.xazhao.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description Created on 2024/11/07.
 * @Author Zhao.An
 */

@Getter
@AllArgsConstructor
public enum ServiceErrorCode implements ErrorCode {

    /**
     * ApplicationContext 初始化错误
     */
    APPLICATION_CONTEXT_ERROR("APPLICATION_CONTEXT_ERROR", "applicationContext 初始化错误"),

    /**
     * HTTP 客户端错误
     */
    HTTP_CLIENT_ERROR("HTTP_CLIENT_ERROR", "HTTP 客户端错误"),

    /**
     * HTTP 服务端错误
     */
    HTTP_SERVER_ERROR("HTTP_SERVER_ERROR", "HTTP 服务端错误"),

    /**
     * 不允许重复发送通知
     */
    SEND_NOTICE_DUPLICATED("SEND_NOTICE_DUPLICATED", "不允许重复发送通知"),

    /**
     * 通知保存失败
     */
    NOTICE_SAVE_FAILED("NOTICE_SAVE_FAILED", "通知保存失败"),

    /**
     * 状态机转换失败
     */
    STATE_MACHINE_TRANSITION_FAILED("STATE_MACHINE_TRANSITION_FAILED", "状态机转换失败"),

    /**
     * 重复请求
     */
    DUPLICATED("DUPLICATED", "重复请求"),

    /**
     * 远程调用返回结果为空
     */
    REMOTE_CALL_RESPONSE_IS_NULL("REMOTE_CALL_RESPONSE_IS_NULL", "远程调用返回结果为空"),

    /**
     * 远程调用返回结果失败
     */
    REMOTE_CALL_RESPONSE_IS_FAILED("REMOTE_CALL_RESPONSE_IS_FAILED", "远程调用返回结果失败"),

    /**
     * 下载失败
     */
    DOWNLOAD_FAILED("DOWNLOAD_FAILED", "下载失败"),

    /**
     * Excel导出失败
     */
    EXCEL_EXPORT_FAILED("EXCEL_EXPORT_FAILED", "Excel导出失败"),

    /**
     * 获取CompletableFuture结果出现异常
     */
    GET_COMPLETABLE_FUTURE_RESULT_FAILED("GET_COMPLETABLE_FUTURE_RESULT_FAILED", "获取CompletableFuture结果出现异常");

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
