package com.hannstar.spring.boot.rocketmq.demo;

import java.util.concurrent.CountDownLatch;

import javax.annotation.Resource;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author jiangxinjun
 * @date 2019-05-07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerUserPropertyTest {
    
    @Resource
    private RocketMQTemplate rocketMQTemplate;
    
    public static CountDownLatch countDownLatch = new CountDownLatch(1);
    
    @Ignore
    @Test
    public void sendMessageWithUserProperty() throws InterruptedException {
        Message<String> message = MessageBuilder.withPayload("test-message").setHeader(RocketMQHeaders.KEYS, "test-key")
                .setHeader("storeId", 1).build();
        rocketMQTemplate.send("test-topic:test-tag", message);
        countDownLatch.await();
    }
}
