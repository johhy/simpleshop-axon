package com.github.johhy.simpleshopaxon.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.johhy.simpleshopaxon.facade.generators.CustomerIdGenerator;
import com.github.johhy.simpleshopaxon.facade.generators.OrderIdGenerator;

@Configuration
public class ApplicationTestConfig {

	@Bean
	public CustomerIdGenerator customerIdGeneratorMock() {
		return new CustomerIdGeneratorMock();
	}
	
	@Bean
	public OrderIdGenerator orderIdGeneratorMock() {
		return new OrderIdGeneratorMock();
	}
	
}
