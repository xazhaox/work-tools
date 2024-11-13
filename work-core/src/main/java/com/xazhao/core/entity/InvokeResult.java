package com.xazhao.core.entity;

import com.xazhao.core.constant.HttpStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description Created on 2024/11/07.
 * @Author Zhao.An
 */

@Getter
@Setter
public class InvokeResult<T> {

    /**
     * 返回值数据
     */
    private T data;

    /**
     * 异常信息
     */
    private String errorMsg;

    /**
     * 返回值错误信息
     */
    private String returnMsg;

    /**
     * 接口返回值代码
     */
    private Integer code;

    /**
     * 登陆接口token
     */
    private String token;

    public InvokeResult(T data, Integer status) {
        this.data = data;
        this.code = status;
    }

    public InvokeResult(Integer status, String message) {
        this.code = status;
        this.returnMsg = message;
    }

    public InvokeResult(T data, Integer status, String message) {
        this.data = data;
        this.code = status;
        this.returnMsg = message;
    }

    public InvokeResult(Integer status, String returnMsg, String errorMsg) {
        this.code = status;
        this.returnMsg = returnMsg;
        this.errorMsg = errorMsg;
    }

    public static <T> InvokeResult<T> success(T data) {
        return new InvokeResult<>(data, HttpStatus.SUCCESS);
    }

    public static <T> InvokeResult<T> success(String message) {
        return new InvokeResult<>(HttpStatus.SUCCESS, message);
    }

    public static <T> InvokeResult<T> success(T data, String message) {
        return new InvokeResult<>(data, HttpStatus.SUCCESS, message);
    }

    public static <T> InvokeResult<T> success() {
        return new InvokeResult<>(HttpStatus.SUCCESS, "Successful");
    }

    public static <T> InvokeResult<T> failure(T data, String message) {
        return new InvokeResult<>(data, HttpStatus.ERROR, message);
    }

    public static <T> InvokeResult<T> failure(String message) {
        return new InvokeResult<>(HttpStatus.ERROR, message);
    }

    public static <T> InvokeResult<T> failure() {
        return new InvokeResult<>(HttpStatus.ERROR, "Request failed");
    }

    public static <T> InvokeResult<T> failure(String returnMsg, String errorMsg) {
        return new InvokeResult<>(HttpStatus.ERROR, returnMsg, errorMsg);
    }
}
