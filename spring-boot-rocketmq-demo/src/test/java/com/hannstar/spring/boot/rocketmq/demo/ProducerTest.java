package com.hannstar.spring.boot.rocketmq.demo;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;

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

/**
 * @author jiangxinjun
 * @date 2019-05-07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerTest {
    
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    
    public static CountDownLatch countDownLatch = new CountDownLatch(1);
    
    @Ignore
    @Test
    public void sendMessage() throws InterruptedException {
        Message<String> message = MessageBuilder.withPayload("test-message").setHeader(RocketMQHeaders.KEYS, "test-key").build();
        rocketMQTemplate.send("test-topic:test-tag", message);
        countDownLatch.await();
    }
    
    @Ignore
    @Test
    public void sendObjectMessage() throws InterruptedException {
        Message<OrderPaidEvent> message = MessageBuilder.withPayload(new OrderPaidEvent("O001", BigDecimal.valueOf(100))).setHeader(RocketMQHeaders.KEYS, "test-key").build();
        rocketMQTemplate.send("test-topic:test-tag", message);
        countDownLatch.await();
    }
    
    @Test
    public void sendObjectMessageOnCustomConsumer() throws InterruptedException {
        Message<OrderPaidEvent> message = MessageBuilder.withPayload(new OrderPaidEvent("O001", BigDecimal.valueOf(100))).setHeader(RocketMQHeaders.KEYS, "test-key").build();
        rocketMQTemplate.send("ORDER_SRV:TEST_TAG", message);
        countDownLatch.await();
    }
    
}
