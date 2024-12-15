package com.xazhao.core.entity.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 通用出参
 *
 * @Description Created on 2024/12/05.
 * @Author Zhao.An
 */

@Setter
@Getter
@ToString
public class BaseResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Boolean success;

    private String responseCode;

    private String responseMessage;
}
