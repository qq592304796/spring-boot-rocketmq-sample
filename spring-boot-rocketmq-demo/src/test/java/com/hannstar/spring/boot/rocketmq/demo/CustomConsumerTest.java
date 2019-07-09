package com.hannstar.spring.boot.rocketmq.demo;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

import org.apache.rocketmq.spring.core.RocketMQListener;
import org.junit.Test;
import org.springframework.aop.framework.AopProxyUtils;

/**
 * @author jiangxinjun
 * @date 2019-05-09
 */
public class CustomConsumerTest {
    
    @Test
    public void getGeneric() {
        CustomConsumer<OrderPaidEvent> rocketMQListener = new CustomConsumer<OrderPaidEvent>() {
            @Override
            public void onMessage(OrderPaidEvent message) {
            }
        };
        //System.out.println(rocketMQListener.getMessageType());
        
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(rocketMQListener);
        Type[] interfaces = targetClass.getGenericInterfaces();
        Class<?> superclass = targetClass.getSuperclass();
        while ((Objects.isNull(interfaces) || 0 == interfaces.length) && Objects.nonNull(superclass)) {
            interfaces = superclass.getGenericInterfaces();
            superclass = targetClass.getSuperclass();
        }
        if (Objects.nonNull(interfaces)) {
            for (Type type : interfaces) {
                if (type instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) type;
                    if (Objects.equals(parameterizedType.getRawType(), RocketMQListener.class)) {
                        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                        if (Objects.nonNull(actualTypeArguments) && actualTypeArguments.length > 0) {
                            System.out.println(actualTypeArguments[0]);
                        }
                    }
                }
            }
        } else {
        }
    }
    
}
