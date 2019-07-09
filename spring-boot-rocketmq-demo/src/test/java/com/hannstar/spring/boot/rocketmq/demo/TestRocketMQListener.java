package com.hannstar.spring.boot.rocketmq.demo;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RocketMQMessageListener(topic = "test-topic", consumerGroup = "test-consumer", selectorExpression = "test-tag")
public class TestRocketMQListener implements RocketMQListener<String> {
    
    public void onMessage(String message) {
        log.info("received message: {}", message);
        ProducerTest.countDownLatch.countDown();
    }
    
}