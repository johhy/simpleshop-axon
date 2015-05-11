package com.github.johhy.simpleshopaxon.test.rest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.github.johhy.simpleshopaxon.test.SpringAbstractSimpleShopAxonControllerTest;
import com.github.johhy.simpleshopaxon.test.config.TestConstants;

public class OrderControllerTest extends SpringAbstractSimpleShopAxonControllerTest {
	
	@Test
	public void testOrderForCustomer() throws Exception {
		restTemplate
			.exchange(host + "/product/eee/quantity/45/price/23.0", 
				HttpMethod.POST, null, String.class);
		restTemplate
			.exchange(host + "/customer/addr1", 
				HttpMethod.POST, null, String.class);
		restTemplate
			.exchange(host + "/customer/" + TestConstants.customerId + 
				"/product/eee/quantity/5/add", 
				HttpMethod.POST, null, String.class);
		ResponseEntity<String> res = restTemplate
				.exchange(host + "/order/customer/" + TestConstants.customerId + 
						"/ship/addr2",
						HttpMethod.POST, null, String.class);
		assertEquals(res.getHeaders().getLocation().getPath(),
				"/order/" + TestConstants.orderId);
		assertEquals(res.getStatusCode(), HttpStatus.CREATED);
	}
	
	@Test
	public void testChangeOrderStatus() throws Exception {
		restTemplate
			.exchange(host + "/product/eee/quantity/45/price/23.0", 
				HttpMethod.POST, null, String.class);
		restTemplate
			.exchange(host + "/customer/addr1", 
				HttpMethod.POST, null, String.class);
		restTemplate
			.exchange(host + "/customer/" + TestConstants.customerId + 
				"/product/eee/quantity/5/add", 
				HttpMethod.POST, null, String.class);
		restTemplate
				.exchange(host + "/order/customer/" + TestConstants.customerId + 
						"/ship/addr2",
						HttpMethod.POST, null, String.class);
		ResponseEntity<String> res = restTemplate
				.exchange(host + "/order/" + TestConstants.orderId + 
						"/status/2",
						HttpMethod.POST, null, String.class);
		assertEquals(res.getStatusCode(), HttpStatus.ACCEPTED);
	}

}
