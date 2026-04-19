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
      private final WebClient payWebClient;

      public String createUser(String userId) {

        if ("slow".equals(userId)) sleep(2000);

        if ("error".equals(userId)) {
            throw new RuntimeException("Intentional error");
        }

        // 🔥 핵심: 전체 흐름 연결
        String orderResult = payWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/order")
                        .queryParam("userId", userId)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(3))
                .block();

        return "User OK → " + orderResult;
      }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {}
    }
}
