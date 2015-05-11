package com.github.johhy.simpleshopaxon.test.query;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.johhy.simpleshopaxon.core.api.commands.ChangeCustomerAddress;
import com.github.johhy.simpleshopaxon.core.api.commands.CreateCustomer;
import com.github.johhy.simpleshopaxon.core.api.commands.CreateProductCell;
import com.github.johhy.simpleshopaxon.core.api.commands.GiveProductToCustomer;
import com.github.johhy.simpleshopaxon.core.api.shared.Address;
import com.github.johhy.simpleshopaxon.core.api.shared.ApplicationCommandGateway;
import com.github.johhy.simpleshopaxon.core.api.shared.Price;
import com.github.johhy.simpleshopaxon.core.api.shared.Product;
import com.github.johhy.simpleshopaxon.query.repository.CustomerTableRepository;
import com.github.johhy.simpleshopaxon.query.tables.CustomerTable;
import com.github.johhy.simpleshopaxon.test.SpringAbstractSimpleShopAxonTest;

import static org.junit.Assert.*;

public class CustomerTableTest extends SpringAbstractSimpleShopAxonTest {

	@Autowired 
	private CustomerTableRepository ctRepo;
	
	@Autowired
	private ApplicationCommandGateway cg;
	
	private TimeUnit unit = TimeUnit.SECONDS;
	private long timeout = 10;
	
	@Test
	public void testCreateCustomer() throws Exception {
		assertTrue(ctRepo.findByCustomerId("c").isEmpty());
		Date created = new Date();
		cg.sendAndWaitException(new CreateCustomer("c", new Address("a"), created), timeout, unit);
		List<CustomerTable> ctl = ctRepo.findByCustomerId("c");
		assertTrue(ctl.size()==1);
		assertTrue("a"==ctl.get(0).getAddress());
		assertTrue(created.equals(ctl.get(0).getCreated()));
	}
	
	@Test
	public void testAddressChanged() throws Exception {
		Date created = new Date();
		cg.sendAndWaitException(new CreateCustomer("c", new Address("a"), created), timeout, unit);
		assertTrue("a"== ctRepo.findByCustomerId("c").get(0).getAddress());
		cg.sendAndWaitException(new ChangeCustomerAddress("c", new Address("new")), timeout, unit);
		assertTrue("new"== ctRepo.findByCustomerId("c").get(0).getAddress());
	}
	
	@Test
	public void testProductChanged() throws Exception {
		Date created = new Date();
		cg.sendAndWaitException(new CreateCustomer("c", new Address("a"), created), timeout, unit);
		assertTrue(1==ctRepo.findByCustomerId("c").size());
		assertNull(ctRepo.findByCustomerIdAndProductId("c", "p"));
		cg.sendAndWaitException(new CreateProductCell(new Product("p", 10, new Price(25.0))), timeout, unit);
		cg.sendAndWaitException(new CreateProductCell(new Product("z", 20, new Price(12.0))), timeout, unit);
		cg.sendAndWaitException(new GiveProductToCustomer(new Product("p", 3, new Price(0.0)), "c"), timeout, unit);
		cg.sendAndWaitException(new GiveProductToCustomer(new Product("z", 5, new Price(0.0)), "c"), timeout, unit);
		assertTrue(2==ctRepo.findByCustomerId("c").size());
		CustomerTable ct = ctRepo.findByCustomerIdAndProductId("c", "p");
		assertNotNull(ct);
		assertEquals("p", ct.getProductId());
		assertTrue(25.0==ct.getPrice());
		assertTrue(3==ct.getQuantity());
	}
}
