package com.hannstar.spring.boot.rocketmq.demo;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@AllArgsConstructor
public class OrderPaidEvent extends BaseMessage {

    private static final long serialVersionUID = 8920434940691062995L;

    private String orderId;

    private BigDecimal paidMoney;
}