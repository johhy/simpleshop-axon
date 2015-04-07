package com.github.johhy.simpleshopaxon.core.domain.test.customer;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.johhy.simpleshopaxon.core.commandgateway.CustomerCommandGateway;
import com.github.johhy.simpleshopaxon.core.commands.customer.AddProductToOrderCommand;
import com.github.johhy.simpleshopaxon.core.commands.customer.ChangeCustomerInfoCommand;
import com.github.johhy.simpleshopaxon.core.commands.customer.CreateCustomerCommand;
import com.github.johhy.simpleshopaxon.core.commands.customer.CreateOrderCommand;
import com.github.johhy.simpleshopaxon.core.commands.customer.RemoveCustomerCommand;
import com.github.johhy.simpleshopaxon.core.commands.customer.RemoveProductFromOrderCommand;
import com.github.johhy.simpleshopaxon.query.customer.CustomerInfoRepository;
import com.github.johhy.simpleshopaxon.query.customer.CustomerRepository;
import com.github.johhy.simpleshopaxon.query.customer.CustomerTable;
import com.github.johhy.simpleshopaxon.test.SpringAbstractSimpleShopAxonTest;

/**
 * @author johhy
 *
 */
public class CustomerQueryTest extends SpringAbstractSimpleShopAxonTest {
	
	@Autowired
	private CustomerCommandGateway customerCG;
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private CustomerInfoRepository customerInfoRepo;

	@Test
	public void testCreateCustomer() throws Exception {
		assertTrue(customerRepo.findByCustomerId("1").isEmpty());
		assertNull(customerInfoRepo.findByCustomerId("1"));
		customerCG.createCustomer(new CreateCustomerCommand("1", "customerName",
				"customerLogin"));
		assertFalse(customerRepo.findByCustomerId("1").isEmpty());
		assertNotNull(customerInfoRepo.findByCustomerId("1"));
		assertEquals("customerName",customerInfoRepo
				.findByCustomerId("1").getCustomerName());
		assertEquals("customerLogin",customerInfoRepo
				.findByCustomerId("1").getCustomerLogin());
	}
	
	@Test
	public void testDeleteCustomer() throws Exception {
		customerCG.createCustomer(new CreateCustomerCommand("1", "customerName",
				"customerLogin"));
		assertFalse(customerRepo.findByCustomerId("1").isEmpty());
		assertNotNull(customerInfoRepo.findByCustomerId("1"));
		customerCG.removeCustomer(new RemoveCustomerCommand("1"));
		assertTrue(customerRepo.findByCustomerId("1").isEmpty());
		assertNull(customerInfoRepo.findByCustomerId("1"));
	}
	
	@Test
	public void testChangeCustomerInfo() throws Exception {
		customerCG.createCustomer(new CreateCustomerCommand("1", "customerName",
				"customerLogin"));
		assertEquals("customerName",customerInfoRepo
				.findByCustomerId("1").getCustomerName());
		assertEquals("customerLogin",customerInfoRepo
				.findByCustomerId("1").getCustomerLogin());
		customerCG.changeCustomerInfo(new ChangeCustomerInfoCommand("1", 
				"newName", "newLogin"));
		assertEquals("newName",customerInfoRepo
				.findByCustomerId("1").getCustomerName());
		assertEquals("newLogin",customerInfoRepo
				.findByCustomerId("1").getCustomerLogin());
	}
	
	@Test
	public void testCreateOrder() throws Exception {
		customerCG.createCustomer(new CreateCustomerCommand("1", "customerName",
				"customerLogin"));
		assertTrue(customerRepo.findByCustomerIdAndOrderId("1", "order").isEmpty());
		customerCG.createOrder(new CreateOrderCommand("1", "order"));
		assertFalse(customerRepo.findByCustomerIdAndOrderId("1", "order").isEmpty());
	}
	
	@Test
	public void testAddProductToOrder() throws Exception {
		customerCG.createCustomer(new CreateCustomerCommand("1", "customerName",
				"customerLogin"));
		customerCG.createOrder(new CreateOrderCommand("1", "order"));
		assertNull(customerRepo.findByCustomerIdAndOrderIdAndCodeOfProduct("1",
				"order", "codeOfProduct"));
		customerCG.addProductToOrder(new AddProductToOrderCommand("1", "order", 
				"codeOfProduct", 5, 10));
		CustomerTable ct = customerRepo.findByCustomerIdAndOrderIdAndCodeOfProduct("1",
				"order", "codeOfProduct");
		assertTrue(5==ct.getQuantity());
		assertTrue(10==ct.getPrice());
	}
	
	@Test
	public void testRemoveProductFromOrder() throws Exception {
		customerCG.createCustomer(new CreateCustomerCommand("1", "customerName",
				"customerLogin"));
		customerCG.createOrder(new CreateOrderCommand("1", "order"));
		customerCG.addProductToOrder(new AddProductToOrderCommand("1", "order", 
				"codeOfProduct", 5, 10));
		assertNotNull(customerRepo.findByCustomerIdAndOrderIdAndCodeOfProduct("1",
				"order", "codeOfProduct"));
		customerCG.removeProductFromOrder(new RemoveProductFromOrderCommand("1",
				"order", "codeOfProduct", 5));
		assertNull(customerRepo.findByCustomerIdAndOrderIdAndCodeOfProduct("1",
				"order", "codeOfProduct"));
		
	}
}
