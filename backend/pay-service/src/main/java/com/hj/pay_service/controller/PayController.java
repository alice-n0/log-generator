package com.hj.pay_service.controller;

import org.springframework.web.bind.annotation.*;

import com.hj.pay_service.service.PayService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("pay")
@RequiredArgsConstructor
public class PayController {

    private final PayService payService;

    @GetMapping
    public String pay(@RequestParam("userId") String userId) throws InterruptedException {
        return payService.processPay(userId);
    }
}