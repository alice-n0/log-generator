package com.hj.user_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hj.user_service.model.User;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("user")
public class UserContoller {

    @GetMapping
    public User getUser(@RequestParam("userId") String userId) throws InterruptedException {
    
        // case 1. 특정 유저(=slow) 지연
        if("slow".equals(userId)) Thread.sleep(2000);
        // case 2. 일부 요청(=error) 에러
        if ("error".equals(userId)) throw new RuntimeException("Intentional error");
        return new User(userId, "User-" + userId);
    }
    
}
