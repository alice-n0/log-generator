package com.hj.user_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final WebClient orderWebClient;

    @GetMapping
    public String getUser(@RequestParam("userId") String userId) {

        if ("slow".equals(userId)) sleep(2000);

        if ("error".equals(userId)) {
            throw new RuntimeException("Intentional error");
        }

        // 🔥 핵심: 전체 흐름 연결
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
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {}
    }
}
