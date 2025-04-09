package com.redone.messages_router.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "ibm.mq")
@Data
public class MQConfigurationProperties {
    private String queueManager;
    private String channel;
    private String connName;
    private String user;
    private String password;
    private String queue;
    private long receiveTimeout;
}