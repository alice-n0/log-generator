package com.hj.order_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hj.order_service.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
@Tag(name = "Order", description = "주문 API")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @Operation(
            summary = "주문 생성",
            description = "userId를 받아 결제 서비스 호출 결과를 포함한 주문 생성 메시지를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 생성 성공",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "결제 서비스 호출 실패")
    })
    public String createOrder(
            @Parameter(description = "주문 생성 대상 사용자 ID", required = true, example = "user-123")
            @RequestParam("userId") String userId) {
        return orderService.createOrder(userId);
    }
}
