package com.github.johhy.simpleshopaxon.test.query;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.johhy.simpleshopaxon.core.api.commands.ChangeOrderStatus;
import com.github.johhy.simpleshopaxon.core.api.commands.CreateCustomer;
import com.github.johhy.simpleshopaxon.core.api.commands.CreateOrderForCustomer;
import com.github.johhy.simpleshopaxon.core.api.commands.CreateProductCell;
import com.github.johhy.simpleshopaxon.core.api.commands.GiveProductToCustomer;
import com.github.johhy.simpleshopaxon.core.api.shared.Address;
import com.github.johhy.simpleshopaxon.core.api.shared.ApplicationCommandGateway;
import com.github.johhy.simpleshopaxon.core.api.shared.History;
import com.github.johhy.simpleshopaxon.core.api.shared.OrderStatus;
import com.github.johhy.simpleshopaxon.core.api.shared.Price;
import com.github.johhy.simpleshopaxon.core.api.shared.Product;
import com.github.johhy.simpleshopaxon.query.repository.OrderTableRepository;
import com.github.johhy.simpleshopaxon.query.tables.OrderTable;
import com.github.johhy.simpleshopaxon.test.SpringAbstractSimpleShopAxonTest;

public class OrderTableTest extends SpringAbstractSimpleShopAxonTest {
	
	@Autowired
	private OrderTableRepository otRepo;
	
	@Autowired
	private ApplicationCommandGateway cg;
	
	private TimeUnit unit = TimeUnit.SECONDS;
	private long timeout = 10;
	
	@Test
	public void testCreateOrder() throws Exception {
		assertTrue(otRepo.findByOrderId("o").isEmpty());
		cg.sendAndWaitException(new CreateProductCell(new Product("p", 40, new Price(20.0))), timeout, unit);
		cg.sendAndWaitException(new CreateProductCell(new Product("z", 30, new Price(10.0))), timeout, unit);
		cg.sendAndWaitException(new CreateCustomer("c", new Address("a"), new Date()), timeout, unit);
		cg.sendAndWaitException(new GiveProductToCustomer(new Product("p", 5, new Price(0.0)),
				"c"), timeout, unit);
		cg.sendAndWaitException(new GiveProductToCustomer(new Product("z", 10, new Price(0.0)),
				"c"), timeout, unit);
		cg.sendAndWaitException(new CreateOrderForCustomer("c", "o", new Date(), 
				new Address("shipTo")), timeout, unit);
		List<OrderTable> otl = otRepo.findByOrderId("o");
		assertFalse(otl.isEmpty());
		assertTrue(2==otl.size());
		OrderTable ot = otRepo.findByOrderIdAndProductId("o", "p");
		assertTrue(5==ot.getQuantity());
		assertTrue(20.0==ot.getPrice());
		assertEquals("c", ot.getCustomerId());
		assertEquals("CREATED", ot.getOrderSatus());
	}
	
	@Test
	public void testChangeOrderStatus() throws Exception {
		cg.sendAndWaitException(new CreateProductCell(new Product("p", 40, new Price(20.0))), timeout, unit);
		cg.sendAndWaitException(new CreateCustomer("c", new Address("a"), new Date()), timeout, unit);
		cg.sendAndWaitException(new GiveProductToCustomer(new Product("p", 5, new Price(0.0)),
				"c"), timeout, unit);
		cg.sendAndWaitException(new CreateOrderForCustomer("c", "o", new Date(10000), 
				new Address("shipTo")), timeout, unit);
		OrderTable ot = otRepo.findByOrderIdAndProductId("o", "p");
		assertEquals("CREATED", ot.getOrderSatus());
		cg.sendAndWaitException(new ChangeOrderStatus("o", new History(new Date(20000), 
				OrderStatus.DELIVERED)), timeout, unit);
		OrderTable ot1 = otRepo.findByOrderIdAndProductId("o", "p");
		assertEquals("DELIVERED", ot1.getOrderSatus());
	}

}
