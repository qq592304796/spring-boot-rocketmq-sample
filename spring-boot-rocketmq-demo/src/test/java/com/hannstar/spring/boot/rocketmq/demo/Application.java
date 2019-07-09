package com.hannstar.spring.boot.rocketmq.demo;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.hannstar.spring.boot.rocketmq.demo.transaction.DefaultRocketMQLocalTransactionListener;

/**
 * @author jiangxinjun
 * @date 2019-05-07
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public DefaultMQPushConsumer defaultMQPushConsumer(RocketMQProperties rocketMQProperties)
            throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ORDER_SRV_CONSUMER_GROUP", true, null);
        consumer.setNamesrvAddr(rocketMQProperties.getNameServer());
        consumer.setMessageModel(org.apache.rocketmq.common.protocol.heartbeat.MessageModel.CLUSTERING);
        consumer.subscribe("ORDER_SRV", "TEST_TAG");
        consumer.setMessageListener(defaultMessageListenerConcurrently());
        return consumer;
    }
    
    @Bean
    public DefaultMessageListenerConcurrently defaultMessageListenerConcurrently() {
        return new DefaultMessageListenerConcurrently();
    }
    
    @Bean
    public DefaultRocketMQLocalTransactionListener defaultRocketMQLocalTransactionListener(RocketMQTemplate rocketMQTemplate) {
        DefaultRocketMQLocalTransactionListener defaultRocketMQLocalTransactionListener = new DefaultRocketMQLocalTransactionListener();
        String txProducerGroup = rocketMQTemplate.getProducer().getProducerGroup() + "_TRANSACTION";
        rocketMQTemplate.createAndStartTransactionMQProducer(txProducerGroup, defaultRocketMQLocalTransactionListener, new ThreadPoolExecutor(1, 1,
                1000 * 60, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(2000)), null);
        return defaultRocketMQLocalTransactionListener;
    }
    
}