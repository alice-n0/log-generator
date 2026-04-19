package com.hj.user_service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.reactive.function.client.WebClient;

import com.hj.user_service.service.UserService;

@SpringBootTest
@TestPropertySource(properties = "PAY_SERVICE_URL=/pay")
class UserServiceApplicationTests {

	@MockBean
	private WebClient webClient;

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
		assertNotNull(userService);
	}

}
