package com.hannstar.spring.boot.rocketmq.demo.transaction;

import java.math.BigDecimal;

import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import com.hannstar.spring.boot.rocketmq.demo.OrderPaidEvent;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jiangxinjun
 * @date 2019-05-07
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerTransactionTest {
    
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    
    @Ignore
    @Test
    public void sendTransactionMessage() throws InterruptedException {
        Message<OrderPaidEvent> message = MessageBuilder.withPayload(new OrderPaidEvent("O001", BigDecimal.valueOf(100))).setHeader(RocketMQHeaders.KEYS, "test-key").build();
        TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction("test", "test-topic:test-tag1", message, null);
        log.info("result {}", transactionSendResult);
    }
    
    @Test
    public void sendCustomeTransactionMessage() throws InterruptedException {
        Message<OrderPaidEvent> message = MessageBuilder.withPayload(new OrderPaidEvent("O001", BigDecimal.valueOf(100))).setHeader(RocketMQHeaders.KEYS, "test-key").build();
        TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction("test-producer_TRANSACTION", "test-topic:test-tag1", message, null);
        log.info("result {}", transactionSendResult);
    }
}
