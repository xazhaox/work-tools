package com.xazhao.cache.exception;

import com.xazhao.core.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description Created on 2024/12/02.
 * @Author Zhao.An
 */

@Getter
@AllArgsConstructor
public enum DistributeLockErrorCode implements ErrorCode {

    /**
     * 没有找到锁
     */
    NO_LOCK_KEY_FOUND("NO_LOCK_KEY_FOUND", "no lock key found..."),

    /**
     * 获取锁失败
     */
    ACQUIRE_LOCK_FAILED("acquire lock failed... key", "acquire lock failed... key");

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
