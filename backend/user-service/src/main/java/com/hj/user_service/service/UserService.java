package com.hj.user_service.service;

import java.time.Duration;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final WebClient orderWebClient;

    public String createUser(String userId) {

        log.info("User request userId={}", userId);

        // 1️⃣ entry latency (옵션)
        if ("slow".equals(userId)) {
            sleep(2000);
        }

        try {
            // 2️⃣ order-service 호출 (핵심)
            String orderResult = orderWebClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/order")
                            .queryParam("userId", userId)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(3))
                    .block();

            return "User OK → " + orderResult;

        } catch (Exception e) {
            log.error("Order call failed userId={}", userId, e);
            throw new RuntimeException("ERROR: Order API failed", e);
        }
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {}
    }
}
