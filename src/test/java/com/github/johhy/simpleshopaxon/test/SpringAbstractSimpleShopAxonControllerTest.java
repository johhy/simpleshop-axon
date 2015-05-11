package com.github.johhy.simpleshopaxon.test;

import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.web.client.RestTemplate;

import com.github.johhy.simpleshopaxon.test.config.TestConstants;

@WebIntegrationTest
abstract public class SpringAbstractSimpleShopAxonControllerTest extends SpringAbstractSimpleShopAxonTest {

	protected RestTemplate restTemplate = new TestRestTemplate();
	
	protected final String host = TestConstants.host;
	
}
