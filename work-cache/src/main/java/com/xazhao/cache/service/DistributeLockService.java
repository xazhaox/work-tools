package com.xazhao.cache.service;

import com.xazhao.cache.entity.request.PayCreateRequest;

/**
 * @Description Created on 2024/12/02.
 * @Author Zhao.An
 */

public interface DistributeLockService {

    void generatePayUrl(PayCreateRequest payCreateRequest) throws InterruptedException;
}
