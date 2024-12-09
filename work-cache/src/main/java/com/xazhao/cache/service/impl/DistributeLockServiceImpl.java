package com.xazhao.cache.service.impl;

import com.xazhao.cache.annotation.lock.DistributeLock;
import com.xazhao.cache.entity.request.PayCreateRequest;
import com.xazhao.cache.service.DistributeLockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description Created on 2024/12/02.
 * @Author Zhao.An
 */

@Slf4j
@Service
public class DistributeLockServiceImpl implements DistributeLockService {

    @DistributeLock(keyExpression = "#payCreateRequest.bizNo", scene = "GENERATE_PAY_URL")
    @Override
    public void generatePayUrl(PayCreateRequest payCreateRequest) {

    }
}
