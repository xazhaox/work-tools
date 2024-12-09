package com.xazhao.cache.exception;

import com.xazhao.core.exception.ErrorCode;
import com.xazhao.core.exception.SystemException;

/**
 * @Description Created on 2024/12/02.
 * @Author Zhao.An
 */

public class DistributeLockException extends SystemException {

    public DistributeLockException(ErrorCode errorCode) {
        super(errorCode);
    }

    public DistributeLockException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public DistributeLockException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public DistributeLockException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }

    public DistributeLockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
