package com.jianghu.winter.config.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author daniel.hu
 * @date 2019/7/25 14:46
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "group-winter-tag2", selectorExpression = "tag2", topic = "topic-test1")
public class ModuleRocketMQListener2 implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("received tag2 message: {}", message);
        // TODO message handler
    }
}
