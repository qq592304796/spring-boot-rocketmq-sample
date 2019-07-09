package com.hannstar.spring.boot.rocketmq.demo.transaction;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RocketMQMessageListener(topic = "test-topic", consumerGroup = "test-consumer1", selectorExpression = "test-tag1")
public class TestTransactionRocketMQListener implements RocketMQListener<String> {

    public void onMessage(String message) {
        log.info("TestTransactionRocketMQListener received message: {}", message);
    }

}