package com.hj.order_service.service;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final WebClient payWebClient;

    public String createOrder(String userId) {
        // 1️. Latency 시나리오
        if ("slow".equals(userId)) {
            sleep(3000);
        }

        try {
            // 2. Payment 서비스 호출 (장애 전파 핵심)
            String result = payWebClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/pay")
                            .queryParam("userId", userId)
                            .build())
                    .retrieve()
                     .onStatus(HttpStatusCode::is5xxServerError, res ->
                            Mono.error(new RuntimeException("Payment 5xx error")))
                    .onStatus(HttpStatusCode::is4xxClientError, res ->
                            Mono.error(new RuntimeException("Payment 4xx error")))
                    .bodyToMono(String.class)
                    .block();

            return "Order created for user " + userId + ", pay status: " + result;

        } catch (Exception e) {
            // 4️⃣ 장애 전파 (중요)
            log.error("Payment API failed userId={}", userId, e);
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