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
@RocketMQMessageListener(consumerGroup = "group-winter-tag1", selectorExpression = "tag1", topic = "topic-test1")
public class ModuleRocketMQListener implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("received tag1 message: {}", message);
        // TODO message handler
    }
}
