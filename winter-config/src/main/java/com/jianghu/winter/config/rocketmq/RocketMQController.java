package com.jianghu.winter.config.rocketmq;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author daniel.hu
 * @date 2019/7/25 15:04
 */
@Slf4j
@RestController
@Api(tags = "mq")
public class RocketMQController {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/send")
    public void sendMessage() {
        log.info("-----------生产者开始生产消息-----------");
        String topic1 = "topic-test1";
        String message1 = "message1";
        rocketMQTemplate.convertAndSend(topic1, message1);
        rocketMQTemplate.convertAndSend(topic1 + ":tag1", message1 + "tag1content");
        rocketMQTemplate.convertAndSend(topic1 + ":tag2", message1 + "tag2content");
        log.info("生产者生产第一个消息完成: 主题:{}, 内容:{}", topic1, message1);

        String topic2 = "topic-test2";
        String message2 = "message2";
        rocketMQTemplate.convertAndSend(topic2, message2);
        log.info("生产者生产第一个消息完成: 主题:{}, 内容:{}", topic2, message2);
        log.info("-----------生产者生产消息结束-----------");
    }
}
