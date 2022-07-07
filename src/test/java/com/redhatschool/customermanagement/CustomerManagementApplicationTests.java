package com.redhatschool.customermanagement;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.redhatschool.customermanagement.controller.CustomerController;
import com.redhatschool.customermanagement.controller.HelloController;

@SpringBootTest
class CustomerManagementApplicationTests {
	
	@Autowired
	private CustomerController customerController;
	
	@Autowired
	private HelloController helloController;

	@Test
	void contextLoads() {
		assertThat(customerController).isNotNull();
		assertThat(helloController).isNotNull();
	}

}
