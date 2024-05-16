package com.allanweber.customers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestCustomersApplication {

	public static void main(String[] args) {
		SpringApplication.from(CustomersApplication::main).with(TestCustomersApplication.class).run(args);
	}

}
