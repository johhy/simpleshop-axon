package com.github.johhy.simpleshopaxon.test.facade;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.github.johhy.simpleshopaxon.facade.FacadeCommandService;
import com.github.johhy.simpleshopaxon.query.repository.CustomerTableRepository;
import com.github.johhy.simpleshopaxon.query.repository.ProductTableRepository;
import com.github.johhy.simpleshopaxon.test.SpringAbstractSimpleShopAxonTest;

import static org.junit.Assert.*;

public class FacadeCommandServiceTest extends SpringAbstractSimpleShopAxonTest{

	@Autowired
	private FacadeCommandService fcs;
	
	@Autowired
	private ProductTableRepository pRepo;
	
	@Autowired
	private CustomerTableRepository ctRepo;
	
	@Autowired
	private ApplicationContext ac;
	
	@Test
	public void testCreateProduct() throws Exception {
		fcs.createProduct("p", 10, 34.0);
	}
	
	@Test
	public void testCreateOrder() throws Exception {
		fcs.createProduct("p", 10, 34.0);
		String customerId = fcs.createCustomer("addr1");
		assertEquals("c1", customerId);
		fcs.addProductToCustomerShoppingCart("p", 4, customerId);
		String orderId = fcs.createOrderFromShoppingCartForCustomer(customerId, "addr2");
		assertEquals("o1", orderId);
	}
}
