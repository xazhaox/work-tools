package com.xazhao.core.exception;

import com.xazhao.core.entity.InvokeResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <h3>自定义全局异常</h3>
 * <ol>
 *     <li>BusinessException 业务异常</li>
 *     <li>SystemException 系统异常</li>
 * </ol>
 *
 * @Description Created on 2024/11/07.
 * @Author Zhao.An
 */

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Valid注解，参数校验异常处理器
     *
     * @param ex  MethodArgumentNotValidException
     * @param <T> 返回类型
     * @return InvokeResult
     */
    @ResponseBody
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public <T> InvokeResult<T> methodArgumentNotValidExceptions(MethodArgumentNotValidException ex) {

        StringBuilder returnMsg = new StringBuilder("校验未通过项：");
        ex.getBindingResult().getAllErrors().forEach(error -> returnMsg.append(error.getDefaultMessage()).append("，"));
        returnMsg.setLength(returnMsg.length() - 1);
        return InvokeResult.failure(returnMsg.toString());
    }

    /**
     * 业务异常
     *
     * @param ex  BusinessException
     * @param <T> 返回类型
     * @return InvokeResult
     */
    @ResponseBody
    @ExceptionHandler(value = {BusinessException.class})
    public <T> InvokeResult<T> serviceExceptions(BusinessException ex) {

        return InvokeResult.failure(ex.getMessage());
    }

    /**
     * 系统异常
     *
     * @param ex  SystemException
     * @param <T> 返回类型
     * @return InvokeResult
     */
    @ResponseBody
    @ExceptionHandler(value = {SystemException.class})
    public <T> InvokeResult<T> systemExceptions(SystemException ex) {

        return InvokeResult.failure(ex.getMessage());
    }

    /**
     * 处理系统异常，兜底处理所有的一切
     */
    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    public <T> InvokeResult<T> defaultExceptionHandler(Throwable ex) {

        log.error("[Exception：]", ex);
        return InvokeResult.failure(ex.getMessage());
    }
}
