package com.github.johhy.simpleshopaxon.test.config;

import com.github.johhy.simpleshopaxon.facade.generators.OrderIdGenerator;

public class OrderIdGeneratorMock implements OrderIdGenerator {

	@Override
	public String getOrderId() {
		return TestConstants.orderId;
	}

}
