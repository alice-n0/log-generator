package com.hj.order_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    @Value("${PAYMENT_SERVICE_URL}")
    private String payServiceUrl;

    private final RestTemplate restTemplate;

    public String createOrder(String userId) {
        // pay service 호출
        String result = restTemplate.getForObject(payServiceUrl + "?userId=" + userId, String.class);
        return "Order created for user " + userId + ", pay status: " + result;
    }
}