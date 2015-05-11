package com.github.johhy.simpleshopaxon.test.rest;

import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.github.johhy.simpleshopaxon.test.SpringAbstractSimpleShopAxonControllerTest;

import static org.junit.Assert.*;


public class ProductCellControllerTest extends SpringAbstractSimpleShopAxonControllerTest {

	/*
	@Test
	public void testCreateOrder() {
		URI u1 = restTemplate
				.postForLocation("http://localhost:8080/productcell/eee/quantity/45/price/23.0", null);
		String productId = u1.getPath().split("/")[2];
		System.out.println("ProductId:" + productId);
		URI u2 = restTemplate
				.postForLocation("http://localhost:8080/customer/addr1", null);
		String customerId = u2.getPath().split("/")[2];
		ResponseEntity<String> out1 = restTemplate
				.exchange("http://localhost:8080/customer/" + customerId + 
						"/product/" + productId + 
						"/quantity/15/add", HttpMethod.POST, null, String.class);
		 System.out.println("Status:" + out1.getStatusCode());
		 ResponseEntity<String> out2 = restTemplate
					.exchange("http://localhost:8080/order/customer/" + customerId + 
							"/ship/addr2", HttpMethod.POST, null, String.class);
			 System.out.println("Status:" + out2.getStatusCode());
			 
	}
	*/
	
	@Test
	public void testCreateProductCell() throws Exception {
		ResponseEntity<String> res = restTemplate
				.exchange(host + "/product/eee/quantity/45/price/23.0", 
						HttpMethod.POST, null, String.class);
		assertEquals(res.getStatusCode(), HttpStatus.CREATED);
		assertEquals(res.getHeaders().getLocation().getPath(), "/product/eee");
	}
	
	@Test
	public void testChangeCapacityOfProductCell() throws Exception {
		restTemplate.exchange(host + "/product/eee/quantity/45/price/23.0", 
				HttpMethod.POST, null, String.class);
		ResponseEntity<String> res = restTemplate
				.exchange(host + "/product/eee/capacity/100", 
						HttpMethod.POST, null, String.class);
		assertEquals(res.getStatusCode(), HttpStatus.ACCEPTED);
	}
	
	@Test
	public void testAddProductToProductCell() throws Exception {
		restTemplate.exchange(host + "/product/eee/quantity/45/price/23.0", 
				HttpMethod.POST, null, String.class);
		restTemplate.exchange(host + "/product/eee/capacity/100", 
						HttpMethod.POST, null, String.class);
		ResponseEntity<String> res = restTemplate
				.exchange(host + "/product/eee/quantity/10/add", 
						HttpMethod.POST, null, String.class);
		assertEquals(res.getStatusCode(), HttpStatus.ACCEPTED);
	}
	
	@Test
	public void testRemoveProductToProductCell() throws Exception {
		restTemplate.exchange(host + "/product/eee/quantity/45/price/23.0", 
				HttpMethod.POST, null, String.class);
		restTemplate.exchange(host + "/product/eee/capacity/100", 
						HttpMethod.POST, null, String.class);
		ResponseEntity<String> res = restTemplate
				.exchange(host + "/product/eee/quantity/5/remove", 
						HttpMethod.POST, null, String.class);
		assertEquals(res.getStatusCode(), HttpStatus.ACCEPTED);
	}
	
	@Test
	public void testChangePriceProductCell() throws Exception {
		restTemplate.exchange(host + "/product/eee/quantity/45/price/23.0", 
				HttpMethod.POST, null, String.class);
		ResponseEntity<String> res = restTemplate
				.exchange(host + "/product/eee/price/20.0", 
						HttpMethod.POST, null, String.class);
		assertEquals(res.getStatusCode(), HttpStatus.ACCEPTED);
	}
}
