package com.jianghu.winter.config.cache;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * @author daniel.hu
 * @date 2019/7/17 13:35
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisListenerConfig {

    private static final String KEY_EVENT_0_EXPIRED = "__keyevent@0__:expired";

    @Bean
    RedisMessageListenerContainer container(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(lettuceConnectionFactory);
        container.addMessageListener(new RedisKeyExpiredListener(), new PatternTopic(KEY_EVENT_0_EXPIRED));
        return container;
    }
}
