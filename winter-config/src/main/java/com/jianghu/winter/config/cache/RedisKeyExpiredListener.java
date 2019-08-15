package com.jianghu.winter.config.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.nio.charset.StandardCharsets;

/**
 * @author daniel.hu
 * @date 2019/7/17 13:37
 */
@Slf4j
public class RedisKeyExpiredListener implements MessageListener {

    /**
     * 监听订阅的topic，针对redis数据失效事件，进行数据处理
     */
    @Override
    public void onMessage(Message message, byte[] bytes) {
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
        // 过期的key(java中不可能得到这个redis-key对应的redis-value)
        String key = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("redis key 过期：pattern为{}, channel为{}, key为{}", new String(bytes), channel, key);
    }

}
