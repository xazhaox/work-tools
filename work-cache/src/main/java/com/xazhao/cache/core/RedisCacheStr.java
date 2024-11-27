package com.xazhao.cache.core;

import cn.hutool.core.collection.CollUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @Description Created on 2024/11/27.
 * @Author Zhao.An
 */

@Slf4j
public class RedisCacheStr {

    @Resource
    private StringRedisTemplate redisTemplateStr;

    /**
     * 设置缓存有效时间（缓存失效时间），单位为秒 {@link TimeUnit#SECONDS}
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return {@link Boolean#TRUE} 设置成功，{@link Boolean#FALSE} 设置失败
     */
    public Boolean expire(final String key, final long timeout) {
        try {
            if (timeout > 0) {
                redisTemplateStr.expire(key, timeout, TimeUnit.SECONDS);
            }
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    /**
     * 设置缓存有效时间（缓存失效时间），时间单位自定义
     *
     * @param key      Redis键
     * @param timeout  超时时间
     * @param timeUnit 时间单位 {@link TimeUnit}
     * @return {@link Boolean#TRUE} 设置成功，{@link Boolean#FALSE} 设置失败
     */
    public Boolean expire(final String key, final long timeout, final TimeUnit timeUnit) {
        try {
            if (timeout > 0) {
                redisTemplateStr.expire(key, timeout, timeUnit);
            }
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    /**
     * 根据key获取过期时间
     *
     * @param key 缓存Key
     * @return 时间，单位为秒 {@link TimeUnit#SECONDS}，返回0表示为永久有效
     */
    public Long getExpire(String key) {

        return redisTemplateStr.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 缓存基本数据对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
    public void setCache(final String key, final String value) {

        redisTemplateStr.opsForValue().set(key, value);
    }

    /**
     * 缓存基本数据对象，Integer、String、实体类等，设置超时时间
     *
     * @param key     缓存的键值
     * @param value   缓存的值
     * @param timeout 时间，timeout大于0，如果timeout小于等于0将设置无限期
     */
    public void setCache(final String key, final String value, final long timeout) {

        redisTemplateStr.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 缓存基本数据对象，Integer、String、实体类等，设置超时时间
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间，timeout大于0，如果timeout小于等于0将设置无限期
     * @param timeUnit 时间颗粒度
     */
    public void setCache(final String key, final String value, final long timeout, final TimeUnit timeUnit) {

        redisTemplateStr.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 获取缓存基本数据对象
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public String getCache(final String key) {
        ValueOperations<String, String> operation = redisTemplateStr.opsForValue();
        return operation.get(key);
    }

    /**
     * 获取缓存基本数据对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern) {

        return redisTemplateStr.keys(pattern);
    }

    /**
     * 缓存Set类型数据
     *
     * @param key 缓存键值
     * @param set 缓存的数据
     * @return 缓存数据的对象
     */
    public BoundSetOperations<String, String> setCache(final String key, final Set<String> set) {
        BoundSetOperations<String, String> setOperation = redisTemplateStr.boundSetOps(key);
        for (String setValue : set) {
            setOperation.add(setValue);
        }
        return setOperation;
    }

    /**
     * 缓存Set类型数据，设置超时时间
     *
     * @param key     缓存键值
     * @param set     缓存的数据
     * @param timeout 超时时间，单位为秒 {@link TimeUnit#SECONDS}
     * @return 缓存数据的对象
     */
    public BoundSetOperations<String, String> setCache(final String key, final Set<String> set, final long timeout) {
        BoundSetOperations<String, String> setOperation = redisTemplateStr.boundSetOps(key);
        for (String setValue : set) {
            setOperation.add(setValue);
        }
        // 设置超时时间
        if (timeout > 0) {
            expire(key, timeout);
        }
        return setOperation;
    }

    /**
     * 获取set类型缓存
     *
     * @param key 缓存键值
     * @return 缓存数据
     */
    public Set<String> getCacheSet(final String key) {

        return redisTemplateStr.opsForSet().members(key);
    }

    /**
     * 根据value从一个set中查询是否存在
     *
     * @param key   键Key
     * @param value 缓存数据
     * @return {@link Boolean#TRUE} 存在，{@link Boolean#FALSE} 不存在
     */
    public Boolean isMemberSet(final String key, final Object value) {
        try {
            return redisTemplateStr.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return 长度
     */
    public Long getSetSize(final String key) {
        try {
            return redisTemplateStr.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键Key
     * @param values 缓存数据，可以是多个
     * @return 移除的个数
     */
    public Long removeSet(final String key, final Object... values) {
        try {
            return redisTemplateStr.opsForSet().remove(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 移除Set的Key
     *
     * @param key 键Key
     * @return 移除的个数
     */
    public Long removeSet(final String key) {
        try {
            return redisTemplateStr.opsForSet().remove(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 缓存ZSet类型数据
     *
     * @param key   缓存键值
     * @param value 缓存的数据
     * @param score 分数
     * @return {@link Boolean#TRUE} 成功，{@link Boolean#FALSE} 失败
     */
    public Boolean setCache(final String key, final String value, final double score) {

        return redisTemplateStr.opsForZSet().add(key, value, score);
    }

    /**
     * 缓存ZSet类型数据
     *
     * @param key     缓存键值
     * @param value   缓存的数据
     * @param score   分数
     * @param timeout 超时时间，单位为秒 {@link TimeUnit#SECONDS}
     * @return {@link Boolean#TRUE} 成功，{@link Boolean#FALSE} 失败
     */
    public Boolean setCache(final String key, final String value, final double score, final long timeout) {
        Boolean isFlag = redisTemplateStr.opsForZSet().add(key, value, score);
        // 设置超时时间
        if (timeout > 0) {
            expire(key, timeout);
        }
        return isFlag;
    }

    /**
     * 获取ZSet类型缓存数据，有序集合中指定范围内的成员集合（按分数从低到高排序）
     *
     * @param key   缓存键值
     * @param start 开始范围
     * @param end   结束范围
     * @return 缓存数据
     */
    public Set<String> rangeZSet(final String key, final long start, final long end) {

        return redisTemplateStr.opsForZSet().range(key, start, end);
    }

    /**
     * 获取ZSet类型缓存数据，有序集合中所有的成员集合（按分数从低到高排序）
     *
     * @param key 缓存键值
     * @return 缓存数据
     */
    public Set<String> rangeZSet(final String key) {

        return redisTemplateStr.opsForZSet().range(key, 0, -1);
    }

    /**
     * 获取ZSet类型缓存数据，有序集合中指定范围内的成员集合（按分数从高到低排序）
     *
     * @param key   缓存键值
     * @param start 开始范围
     * @param end   结束范围
     * @return 缓存数据
     */
    public Set<String> reverseRangeZSet(final String key, final long start, final long end) {

        return redisTemplateStr.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * 获取ZSet类型缓存数据，有序集合中所有的成员集合（按分数从高到低排序）
     *
     * @param key 缓存键值
     * @return 缓存数据
     */
    public Set<String> reverseRangeZSet(final String key) {

        return redisTemplateStr.opsForZSet().reverseRange(key, 0, -1);
    }

    /**
     * 获取ZSet指定键中的数量
     *
     * @param key 缓存键
     * @return 长度
     */
    public Long getZSetSize(final String key) {

        return redisTemplateStr.opsForZSet().size(key);
    }

    /**
     * 获取ZSet有序集合中的成员数量
     *
     * @param key 缓存键
     * @return 长度
     */
    public Long getZSetCard(final String key) {

        return redisTemplateStr.opsForZSet().zCard(key);
    }

    /**
     * 获取ZSet有序集合中指定成员的分数
     *
     * @param key   缓存键
     * @param value 缓存数据
     * @return 分数
     */
    public Double getZSetScore(final String key, final String value) {

        return redisTemplateStr.opsForZSet().score(key, value);
    }

    /**
     * 统计ZSet有序集合中指定分数范围内的成员数量
     *
     * @param key      缓存键
     * @param minScore 最小分数
     * @param maxScore 最大分数
     * @return 个数
     */
    public Long getCountZSet(final String key, final Double minScore, final Double maxScore) {

        return redisTemplateStr.opsForZSet().count(key, minScore, maxScore);
    }

    /**
     * 将ZSet中指定成员的分数增加指定数值
     *
     * @param key   缓存键
     * @param value 缓存数据
     * @param delta 增加的数值
     * @return 分数
     */
    public Double incrementScoreZSet(final String key, final String value, final double delta) {

        return redisTemplateStr.opsForZSet().incrementScore(key, value, delta);
    }

    /**
     * 获取ZSet指定成员在有序集合中的排名（按分数从低到高排序）
     *
     * @param key   缓存键
     * @param value 缓存数据
     * @param <T>   Java类
     * @return 排名
     */
    public <T> Long rankZSet(final String key, final T value) {

        return redisTemplateStr.opsForZSet().rank(key, value);
    }

    /**
     * 获取ZSet指定成员在有序集合中的排名（按分数从高到低排序）
     *
     * @param key   缓存键
     * @param value 缓存数据
     * @param <T>   Java类
     * @return 排名
     */
    public <T> Long reverseRankZSet(final String key, final T value) {

        return redisTemplateStr.opsForZSet().reverseRank(key, value);
    }

    /**
     * 从ZSet有序集合中移除指定的成员
     *
     * @param key    缓存键
     * @param values 缓存数据集
     * @return 移除数量
     */
    public Long removeZSet(final String key, final Object... values) {

        return redisTemplateStr.opsForZSet().remove(key, values);
    }

    /**
     * 移除指定的ZSet缓存键
     *
     * @param key 缓存键
     * @return 移除数量
     */
    public Long removeZSet(final String key) {

        return redisTemplateStr.opsForZSet().remove(key);
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public long setCache(final String key, final List<String> dataList) {
        Long count = redisTemplateStr.opsForList().leftPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 缓存List数据，指定超时时间
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @param timeout  超时时间，单位为秒 {@link TimeUnit#SECONDS}
     * @return 缓存的对象
     */
    public long setCache(final String key, final List<String> dataList, final long timeout) {
        Long count = redisTemplateStr.opsForList().leftPushAll(key, dataList);
        // 设置超时时间
        if (timeout > 0) {
            expire(key, timeout);
        }
        return count == null ? 0 : count;
    }

    /**
     * 将List放入缓存
     *
     * @param key   键（Key）
     * @param value 缓存数据
     * @return {@link Boolean#TRUE} 成功，{@link Boolean#FALSE} 失败
     */
    public Boolean setCacheRight(final String key, final String value) {
        try {
            redisTemplateStr.opsForList().leftPush(key, value);
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    /**
     * 将List放入缓存，指定超时时间
     *
     * @param key     键（Key）
     * @param value   缓存数据
     * @param timeout 超时时间，单位为秒 {@link TimeUnit#SECONDS}
     * @return {@link Boolean#TRUE} 成功，{@link Boolean#FALSE} 失败
     */
    public Boolean setCacheRight(final String key, final String value, final long timeout) {
        try {
            redisTemplateStr.opsForList().leftPush(key, value);
            // 设置超时时间
            if (timeout > 0) {
                expire(key, timeout);
            }
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    /**
     * 获取List缓存的长度
     *
     * @param key 键（Key）
     */
    public Long getListSize(final String key) {

        return redisTemplateStr.opsForList().size(key);
    }

    /**
     * 获取缓存的所有List对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public List<String> getCacheList(final String key) {

        return redisTemplateStr.opsForList().range(key, 0, -1);
    }

    /**
     * 获取缓存的指定List对象
     *
     * @param key   缓存的键值
     * @param start 开始索引
     * @param end   结束索引， 0到-1代表所有值
     * @return 缓存键值对应的数据
     */
    public List<String> getCacheList(final String key, final long start, final long end) {

        return redisTemplateStr.opsForList().range(key, start, end);
    }

    /**
     * 移除N个值为value
     *
     * @param key   键（Key）
     * @param count 移除多少个
     * @param value 缓存数据
     * @return 移除的个数
     */
    public Long removeList(final String key, final long count, final Object value) {
        try {
            return redisTemplateStr.opsForList().remove(key, count, value);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }

    }

    /**
     * 删除单个对象（单个Key）
     *
     * @param key 缓存键值
     * @return {@link Boolean#TRUE} 成功，{@link Boolean#FALSE} 失败
     */
    public Boolean deleteCache(final String key) {
        if (isNotBlank(key)) {
            return redisTemplateStr.delete(key);
        }
        return Boolean.FALSE;
    }

    /**
     * 删除集合对象（批量Keys）
     *
     * @param collection 多个对象
     * @return Long
     */
    public Long deleteCache(final Collection<String> collection) {
        if (CollUtil.isNotEmpty(collection)) {
            return redisTemplateStr.delete(collection);
        }
        return 0L;
    }
}
