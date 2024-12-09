package com.xazhao.cache.enums;

/**
 * 支付渠道
 *
 * @Description Created on 2024/12/02.
 * @Author Zhao.An
 */

public enum PayChannel {

    /**
     * 支付宝
     */
    ALIPAY("支付宝"),

    /**
     * 微信
     */
    WECHAT("微信"),

    /**
     * MOCK
     */
    MOCK("MOCK");

    private String value;

    PayChannel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
