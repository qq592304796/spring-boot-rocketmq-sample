package com.hannstar.spring.boot.rocketmq.demo.transaction;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hannstar.spring.boot.rocketmq.demo.OrderPaidEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultRocketMQLocalTransactionListener implements RocketMQLocalTransactionListener {
    
    @Autowired
    private ObjectMapper rocketMQMessageObjectMapper;
    
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        log.info("DefaultRocketMQLocalTransactionListener executeLocalTransaction received message: {}, {}", msg, arg);
        // ... local transaction process, return bollback, commit or unknown
        return RocketMQLocalTransactionState.UNKNOWN;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        log.info("DefaultRocketMQLocalTransactionListener checkLocalTransaction received message: {}", msg);
        // ... check transaction status and return bollback, commit or unknown
        String str = new String((byte[]) msg.getPayload(), Charset.forName("UTF-8"));
        try {
            OrderPaidEvent orderPaidEvent = rocketMQMessageObjectMapper.readValue(str, OrderPaidEvent.class);
            orderPaidEvent.setRelateId(0L);
        } catch (IOException e) {
        }
        return RocketMQLocalTransactionState.COMMIT;
    }

}