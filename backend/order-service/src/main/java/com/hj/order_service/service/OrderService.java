package com.hj.order_service.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final WebClient payWebClient;

    public String createOrder(String userId) {

        String result = payWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/pay")
                        .queryParam("userId", userId)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofMillis(2000))
                .onErrorMap(e -> new RuntimeException("ERROR: Payment API failed", e))
                .block();

        return "Order created for user " + userId + ", pay status: " + result;
    }
}