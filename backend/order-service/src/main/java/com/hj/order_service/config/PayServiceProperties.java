package com.hj.order_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "pay.service")
public class PayServiceProperties {
    private String baseUrl;
    private int timeout;
}