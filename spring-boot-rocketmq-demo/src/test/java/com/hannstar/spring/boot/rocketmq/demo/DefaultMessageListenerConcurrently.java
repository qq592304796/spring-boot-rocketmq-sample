package com.hannstar.spring.boot.rocketmq.demo;

import java.lang.reflect.ParameterizedType;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author jiangxinjun
 * @param <T>
 * @date 2019-05-09
 */
@Slf4j
public class DefaultMessageListenerConcurrently implements MessageListenerConcurrently {
    
    private String charset = "UTF-8";
    
    @Autowired
    private ObjectMapper rocketMQMessageObjectMapper;
    
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        for (MessageExt messageExt : msgs) {
            log.debug("received msg: {}", messageExt);
            try {
                long now = System.currentTimeMillis();
                CustomConsumer<OrderPaidEvent> rocketMQListener = new CustomConsumer<OrderPaidEvent>() {
                    @Override
                    public void onMessage(OrderPaidEvent message) {
                        log.debug("onMessage received msg: {}", message);
                    }
                };
                rocketMQListener.onMessage(doConvertMessage(messageExt, rocketMQListener));
                long costTime = System.currentTimeMillis() - now;
                log.debug("consume {} cost: {} ms", messageExt.getMsgId(), costTime);
            } catch (Exception e) {
                log.warn("consume message failed. messageExt:{}", messageExt, e);
                context.setDelayLevelWhenNextConsume(0);
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }

        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
    
    @SuppressWarnings("unchecked")
    private <T> T doConvertMessage(MessageExt messageExt, CustomConsumer<T> customConsumer) {
        Class<T> messageType = (Class<T>) ((ParameterizedType)customConsumer.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (Objects.equals(messageType, MessageExt.class)) {
            return (T) messageExt;
        } else {
            String str = new String(messageExt.getBody(), Charset.forName(charset));
            if (Objects.equals(messageType, String.class)) {
                return (T) str;
            } else {
                // If msgType not string, use objectMapper change it.
                try {
                    return rocketMQMessageObjectMapper.readValue(str, messageType);
                } catch (Exception e) {
                    log.info("convert failed. str:{}, msgType:{}", str, messageType);
                    throw new RuntimeException("cannot convert message to " + messageType, e);
                }
            }
        }
    }
}