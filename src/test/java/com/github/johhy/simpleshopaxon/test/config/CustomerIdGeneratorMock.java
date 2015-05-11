package com.github.johhy.simpleshopaxon.test.config;

import com.github.johhy.simpleshopaxon.facade.generators.CustomerIdGenerator;

public class CustomerIdGeneratorMock implements CustomerIdGenerator {

	@Override
	public String getCustomerId() {
		return TestConstants.customerId;
	}

}
