package com.hannstar.spring.boot.rocketmq.demo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * @author jiangxinjun
 * @date 2019-05-09
 */
@JsonInclude(Include.NON_NULL) 
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public abstract class BaseMessage implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String topic;
    
    private String tag;
    
    private Long relateId;
    
    @JsonIgnore
    public String getTransactionId() {
        return topic + "_" + tag + "_" + relateId;
    }
    
}
