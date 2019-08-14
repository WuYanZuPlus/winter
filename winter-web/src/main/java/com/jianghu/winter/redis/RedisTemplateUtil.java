package com.jianghu.winter.redis;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;

/**
 * @author daniel.hu
 * @date 2019/7/15 15:03
 */
@Component
public class RedisTemplateUtil<K, V> {
    @Resource
    private RedisTemplate<K, V> redisTemplate;

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     */
    public void set(final K key, final V value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     */
    public void set(final K key, final V value, long time) {
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            set(key, value);
        }
    }

    /**
     * 读取缓存
     *
     * @param key 键
     */
    public Object get(final K key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 读取缓存
     *
     * @param key   键
     * @param clazz 指定clazz
     * @return class
     */
    @SuppressWarnings("unchecked")
    public <T> T get(final K key, Class<T> clazz) {
        V v = redisTemplate.opsForValue().get(key);
        return JSONObject.parseObject(JSONObject.toJSONString(v), clazz);
    }

    /**
     * 删除，根据key精确匹配
     *
     * @param key 键 (可以传一个值 或多个)
     */
    public void delete(final K... key) {
        redisTemplate.delete(Arrays.asList(key));
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 设置失效时间(秒)
     */
    public void expire(final K key, long time) {
        if (time > 0) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(final K key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * key是否存在
     *
     * @param key 键
     */
    public boolean exists(final K key) {
        return redisTemplate.hasKey(key);
    }

}
