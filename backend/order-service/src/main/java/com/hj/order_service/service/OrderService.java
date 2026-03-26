package com.hj.order_service.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final WebClient payWebClient;

    public String createOrder(String userId) {
        // 1️⃣ Latency 시나리오
        if ("slow".equals(userId)) {
            sleep(3000);
        }

        // 2️⃣ DB Pool 점유 시나리오 (간접적으로 대기 유도)
        if ("db-test".equals(userId)) {
            sleep(5000);
        }

        try {
            // 3️⃣ Payment 서비스 호출 (장애 전파 핵심)
            String result = payWebClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/pay")
                            .queryParam("userId", userId)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return "Order created for user " + userId + ", pay status: " + result;

        } catch (Exception e) {
            // 4️⃣ 장애 전파 (중요)
            throw new RuntimeException("ERROR: Payment API failed", e);
        }
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }
}