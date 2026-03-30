package com.hj.order_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient payWebClient(WebClient.Builder builder,
                                 PayServiceProperties prop) {
        return builder
                .baseUrl(prop.getBaseUrl())
                .build();
    }
}
