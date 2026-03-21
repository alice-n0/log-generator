package com.hj.order_service.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class OrderService {
    @Value("${PAYMENT_SERVICE_URL}")
    private String payServiceUrl;

    private final WebClient webClient;

    public String createOrder(String userId) {

        String result = webClient.get()
            .uri(uriBuilder -> uriBuilder
                    .path(payServiceUrl)
                    .queryParam("userId", userId)
                    .build())
            .retrieve()
            .bodyToMono(String.class)
            .timeout(Duration.ofSeconds(2)) // ⏱ timeout
            .onErrorMap(e -> new RuntimeException("Payment API failed", e))
            .block();

        return "Order created for user " + userId + ", pay status: " + result;
    }
}