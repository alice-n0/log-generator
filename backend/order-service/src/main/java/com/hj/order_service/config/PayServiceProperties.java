package com.hj.order_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "pay.service")
public class PayServiceProperties {
    private String baseUrl;
    private int timeout;
}