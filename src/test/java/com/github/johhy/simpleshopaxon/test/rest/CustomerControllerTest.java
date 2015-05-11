package com.github.johhy.simpleshopaxon.test.rest;

import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.github.johhy.simpleshopaxon.test.SpringAbstractSimpleShopAxonControllerTest;
import com.github.johhy.simpleshopaxon.test.config.TestConstants;

import static org.junit.Assert.*;

public class CustomerControllerTest extends SpringAbstractSimpleShopAxonControllerTest {


	@Test
	public void testCreateCustomer() throws Exception {
		ResponseEntity<String> res = restTemplate
				.exchange(host + "/customer/addr1", 
						HttpMethod.POST, null, String.class);
		assertEquals(res.getStatusCode(), HttpStatus.CREATED);
		assertEquals(res.getHeaders().getLocation().getPath(),
				"/customer/" + TestConstants.customerId);
	}
	
	@Test
	public void testChangeCustomerAddress() throws Exception {
		restTemplate
			.exchange(host + "/customer/addr1", 
					HttpMethod.POST, null, String.class);
		ResponseEntity<String> res = restTemplate
				.exchange(host + "/customer/" + TestConstants.customerId +
						"/address/addr2", 
						HttpMethod.POST, null, String.class);
		assertEquals(res.getStatusCode(), HttpStatus.ACCEPTED);
	}
	
	@Test
	public void testAddProductForCustomer() throws Exception {
		restTemplate
			.exchange(host + "/product/eee/quantity/45/price/23.0", 
					HttpMethod.POST, null, String.class);
		restTemplate
			.exchange(host + "/customer/addr1", 
					HttpMethod.POST, null, String.class);
		ResponseEntity<String> res = restTemplate
				.exchange(host + "/customer/" + TestConstants.customerId + 
						"/product/eee/quantity/5/add", 
						HttpMethod.POST, null, String.class);
		assertEquals(res.getStatusCode(), HttpStatus.ACCEPTED);
	}
	
	@Test
	public void testRemoveProductForCustomer() throws Exception {
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
				.exchange(host + "/customer/" + TestConstants.customerId + 
						"/product/eee/quantity/5/remove", 
						HttpMethod.POST, null, String.class);
		assertEquals(res.getStatusCode(), HttpStatus.ACCEPTED);
	}
}
