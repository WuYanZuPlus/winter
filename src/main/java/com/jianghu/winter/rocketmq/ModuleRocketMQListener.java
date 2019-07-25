package com.jianghu.winter.rocketmq;

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
@RocketMQMessageListener(consumerGroup = "group-winter", topic = "topic-test1")
public class ModuleRocketMQListener implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("received message: {}", message);
        // TODO message handler
    }
}
