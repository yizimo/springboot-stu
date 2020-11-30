package com.zimo.springbootstu.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 插入
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key,value);
    }

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key,value);
    }

    /**
     * 获取
     * @param key
     * @return
     */
    public <T> T get(String key,Class<T> tClass) {
        return (T) redisTemplate.boundValueOps(key).get();
    }

    public Object get(String key) {
        return redisTemplate.boundValueOps(key).get();
    }

    /**
     * zset 添加
     * @param key
     * @param value
     * @param score
     * @return
     */
    public boolean zsetAdd(String key, String value, double score) {
        Boolean add = redisTemplate.opsForZSet().add(key, value, score);
        return add;
    }

    /**
     * zset 获取
     * @param key
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>> getZset(String key, Long  time) {
        Set<ZSetOperations.TypedTuple<Object>> set =
                redisTemplate.opsForZSet().rangeByScore(key, 0, time);
        return set;
    }

    /**
     * 返回长度
     * @param key
     * @return
     */
    public Long getZsetLong(String key) {
        Long aLong = redisTemplate.opsForZSet().zCard(key);
        return aLong;
    }

    /**
     * zset 删除
     * @param key
     * @return
     */
    public Long zsetRemove(String key, Long time) {
        Long aLong = redisTemplate.opsForZSet().removeRangeByScore(key, 0, time);
        return aLong;
    }
}
