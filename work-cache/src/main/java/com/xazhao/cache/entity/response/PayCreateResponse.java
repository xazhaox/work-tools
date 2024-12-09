package com.xazhao.cache.entity.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Description Created on 2024/12/02.
 * @Author Zhao.An
 */

@Getter
@Setter
public class PayCreateResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String payOrderId;

    private String payUrl;
}
