package com.hj.order_service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.reactive.function.client.WebClient;

import com.hj.order_service.service.OrderService;

@SpringBootTest
class OrderServiceApplicationTests {
	@MockitoBean
	private WebClient webClient;

	@Autowired
	private OrderService orderService;

	@Test
	void contextLoads() {
		assertNotNull(orderService);
	}

}
