package com.hj.order_service.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(PayServiceProperties.class)
public class WebClientConfig {
    @Bean
    public WebClient payWebClient(PayServiceProperties prop) {
        return WebClient.builder()
                .baseUrl(prop.getBaseUrl())
                .build();
    }

}
