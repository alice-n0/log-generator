package com.hj.pay_service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.hj.pay_service.service.PayService;

@SpringBootTest
class PayServiceApplicationTests {

	@MockBean
    private DataSource dataSource; 
	
	@Autowired
    private PayService payService;

	@Test
	void contextLoads() {
		 assertNotNull(payService);
	}

}
