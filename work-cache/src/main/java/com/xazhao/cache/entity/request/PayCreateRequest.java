package com.xazhao.cache.entity.request;

import com.xazhao.cache.enums.BizOrderType;
import com.xazhao.cache.enums.PayChannel;
import com.xazhao.cache.enums.UserType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description Created on 2024/12/02.
 * @Author Zhao.An
 */

@Getter
@Setter
public class PayCreateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 付款方id
     */
    @NotNull(message = "payerId is null")
    private String payerId;

    /**
     * 付款方id类型
     */
    @NotNull(message = "payerType is null")
    private UserType payerType;

    /**
     * 收款方id
     */
    @NotNull(message = "payeeId is null")
    private String payeeId;

    /**
     * 收款方id类型
     */
    @NotNull(message = "payeeType is null")
    private UserType payeeType;

    /**
     * 业务单号
     */
    @NotNull(message = "bizNo is null")
    private String bizNo;

    /**
     * 业务单号类型
     */
    @NotNull(message = "bizType is null")
    private BizOrderType bizType;

    /**
     * 订单金额
     */
    @NotNull(message = "orderAmount is null")
    private BigDecimal orderAmount;

    /**
     * 支付渠道
     */
    @NotNull(message = "payChannel is null")
    private PayChannel payChannel;

    /**
     * 支付备注
     */
    @NotNull(message = "memo is null")
    private String memo;
}
