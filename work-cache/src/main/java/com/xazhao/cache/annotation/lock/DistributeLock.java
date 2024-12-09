package com.xazhao.cache.annotation.lock;

import com.xazhao.cache.constant.DistributeLockConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分布式锁注解
 *
 * @Description Created on 2024/12/02.
 * @Author Zhao.An
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributeLock {

    /**
     * 锁的场景
     *
     * @return
     */
    String scene();

    /**
     * 加锁的Key，优先获取key()，如果没有，则取keyExpression()
     *
     * @return
     */
    String key() default DistributeLockConstant.NONE_KEY;

    /**
     * <h3>SpEL表达式，示例</h3>
     * <ol>
     *     <li>#id</li>
     *     <li>#payCreateRequest.bizNo</li>
     * </ol>
     * <h3>使用注解示例</h3>
     * <pre>{@code
     *     @DistributeLock(keyExpression = "#payCreateRequest.bizNo", scene = "GENERATE_PAY_URL")
     *     @Override
     *     public void generatePayUrl(PayCreateRequest payCreateRequest) {
     *         Thread.sleep(10000);
     *         // ...
     *     }
     * }</pre>
     * <h3>调用注解所在方法示例</h3>
     * <pre>{@code
     *     @Test
     *     public void generatePayUrlTest() {
     *         PayCreateRequest payCreateRequest = new PayCreateRequest();
     *         payCreateRequest.setBizNo("1863533126300905472");
     *         distributeLockService.generatePayUrl(payCreateRequest);
     *     }
     * }</pre>
     * 其中，keyExpression = "#payCreateRequest.bizNo"表示：PayCreateRequest实体中bizNo字段的值<br/>
     * 所以最终锁的Key为：GENERATE_PAY_URL#1863533126300905472
     *
     * @return
     */
    String keyExpression() default DistributeLockConstant.NONE_KEY;

    /**
     * 超时时间，单位：毫秒
     * 默认情况下不设置超时时间，锁会自动续期
     *
     * @return
     */
    int expireTime() default DistributeLockConstant.DEFAULT_EXPIRE_TIME;

    /**
     * 加锁等待时长，单位：毫秒
     * 默认情况下不设置等待时长，不等待
     *
     * @return
     */
    int waitTime() default DistributeLockConstant.DEFAULT_WAIT_TIME;
}
