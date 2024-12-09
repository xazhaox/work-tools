package com.xazhao.cache.aspect;

import com.xazhao.cache.annotation.lock.DistributeLock;
import com.xazhao.cache.constant.DistributeLockConstant;
import com.xazhao.cache.exception.DistributeLockErrorCode;
import com.xazhao.cache.exception.DistributeLockException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁切面
 *
 * @Description Created on 2024/12/02.
 * @Author Zhao.An
 */

@Slf4j
@Aspect
@Component
public class DistributeLockAspect {

    /**
     * 构造器注入RedissonClient
     */
    private RedissonClient redissonClient;

    public DistributeLockAspect(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * 配置切入点，@Pointcut切入点表达式，使用@DistributeLock注解标识的方法都会进行切入，也可以用通配符配置具体切入的方法名
     */
    @Pointcut("@annotation(com.xazhao.cache.annotation.lock.DistributeLock)")
    public void distributeLockAspectPointCut() {
    }

    /**
     * 环绕通知
     *
     * @param joinPoint 切入点
     * @return Object
     * @throws Exception Exception
     */
    @Around("distributeLockAspectPointCut()")
    public Object distributeLockAround(ProceedingJoinPoint joinPoint) throws Exception {

        // 获取被调用方法的Method对象，通过连接点的签名来获取
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        // 获取被调用方法上的@DistributeLock注解实例
        DistributeLock distributeLock = method.getAnnotation(DistributeLock.class);

        // 获取最终加锁的key
        String keyLock = this.distributeLockUltimatelyKey(joinPoint, method, distributeLock);

        // 获取锁的场景
        String scene = distributeLock.scene();
        // 组合生成最终的锁的key，格式为：场景值 + "#" + 锁的key值
        String lockKey = scene + "#" + keyLock;

        // 获取锁的超时时间
        int expireTime = distributeLock.expireTime();
        // 获取加锁等待时间
        int waitTime = distributeLock.waitTime();

        // 通过Redisson客户端，根据生成的锁的key获取一个可重入锁对象（RLock）
        RLock rLock = redissonClient.getLock(lockKey);
        // 标记是否成功获取锁，初始化为false
        boolean getLockIsFlag = Boolean.FALSE;

        // 如果等待时间等于默认等待时间
        if (waitTime == DistributeLockConstant.DEFAULT_WAIT_TIME) {

            // 如果过期时间也等于默认过期时间
            if (expireTime == DistributeLockConstant.DEFAULT_EXPIRE_TIME) {

                log.info(String.format("lock for key: %s", lockKey));
                // 直接获取锁，阻塞直到获取成功
                rLock.lock();
            } else {

                log.info(String.format("lock for key: %s, expire: %s", lockKey, expireTime));
                // 以指定的过期时间获取锁，阻塞直到获取成功
                rLock.lock(expireTime, TimeUnit.MILLISECONDS);
            }

            // 获取锁成功，标记为true
            getLockIsFlag = Boolean.TRUE;

        } else {

            // 如果等待时间不等于默认等待时间
            if (expireTime == DistributeLockConstant.DEFAULT_EXPIRE_TIME) {

                log.info(String.format("try lock for key: %s, wait: %s", lockKey, waitTime));
                // 尝试在指定等待时间内获取锁，非阻塞获取
                getLockIsFlag = rLock.tryLock(waitTime, TimeUnit.MILLISECONDS);
            } else {

                log.info(String.format("try lock for key: %s, expire: %s , wait: %s", lockKey, expireTime, waitTime));
                // 尝试在指定等待时间和指定过期时间内获取锁，非阻塞获取
                getLockIsFlag = rLock.tryLock(waitTime, expireTime, TimeUnit.MILLISECONDS);
            }
        }

        // 没有成功获取锁
        if (!getLockIsFlag) {
            log.warn(String.format("lock failed for key: %s, expire: %s", lockKey, expireTime));
            // 抛出分布式锁异常，提示获取锁失败，并包含锁的key信息
            throw new DistributeLockException("acquire lock failed, key: " + lockKey, DistributeLockErrorCode.ACQUIRE_LOCK_FAILED);
        }

        // 用于存储目标方法执行后的返回结果
        Object result = null;
        try {
            log.info(String.format("lock success for ke: %s, expire: %s", lockKey, expireTime));

            // 执行目标方法，即执行使用@DistributeLock注解标注的方法
            result = joinPoint.proceed();
        } catch (Throwable e) {

            // 目标方法执行过程中抛出异常
            throw new Exception(e);

        } finally {
            // 释放锁
            rLock.unlock();
            log.info(String.format("unlock for key: %s, expire: %s", lockKey, expireTime));
        }
        return result;
    }

    /**
     * 获取最终加锁的key
     *
     * @param joinPoint      ProceedingJoinPoint
     * @param method         Method
     * @param distributeLock DistributeLock
     * @return Lock key
     */
    public String distributeLockUltimatelyKey(ProceedingJoinPoint joinPoint, Method method, DistributeLock distributeLock) {
        // 获取注解中定义的锁的key值
        String keyLock = distributeLock.key();

        // 如果锁的key值为DistributeLockConstant.NONE_KEY（表示未设置有效key）
        if (DistributeLockConstant.NONE_KEY.equals(keyLock)) {

            // 判断是否使用的有效的SpEL表达式
            if (DistributeLockConstant.NONE_KEY.equals(distributeLock.keyExpression())) {
                // 未找到锁
                throw new DistributeLockException(DistributeLockErrorCode.NO_LOCK_KEY_FOUND);
            }

            // 创建SpEL的解析器
            SpelExpressionParser parserSpel = new SpelExpressionParser();
            // 解析注解中定义的SpEL表达式，得到一个表达式对象
            Expression expression = parserSpel.parseExpression(distributeLock.keyExpression());

            // 创建EvaluationContext，用于在解析表达式时提供变量等上下文信息
            EvaluationContext context = new StandardEvaluationContext();

            // 获取被调用方法的参数值数组
            Object[] args = joinPoint.getArgs();

            // 获取运行时参数的名称
            StandardReflectionParameterNameDiscoverer discoverer = new StandardReflectionParameterNameDiscoverer();
            // 获取被调用方法的参数名称数组
            String[] parameterNames = discoverer.getParameterNames(method);

            // 将方法的参数绑定到EvaluationContext中，以便在解析表达式时可以使用这些参数值
            if (null != parameterNames) {
                for (int i = 0; i < parameterNames.length; i++) {
                    context.setVariable(parameterNames[i], args[i]);
                }
            }

            // 解析表达式，根据上下文信息获取最终的加锁的key
            keyLock = String.valueOf(expression.getValue(context));
        }
        return keyLock;
    }
}
