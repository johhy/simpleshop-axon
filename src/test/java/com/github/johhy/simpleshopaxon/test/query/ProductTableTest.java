package com.github.johhy.simpleshopaxon.test.query;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.johhy.simpleshopaxon.core.api.commands.CreateProductCell;
import com.github.johhy.simpleshopaxon.core.api.commands.RemoveProductFromProductCell;
import com.github.johhy.simpleshopaxon.core.api.shared.ApplicationCommandGateway;
import com.github.johhy.simpleshopaxon.core.api.shared.Price;
import com.github.johhy.simpleshopaxon.core.api.shared.Product;
import com.github.johhy.simpleshopaxon.query.repository.ProductTableRepository;
import com.github.johhy.simpleshopaxon.query.tables.ProductTable;
import com.github.johhy.simpleshopaxon.test.SpringAbstractSimpleShopAxonTest;

import static org.junit.Assert.*;

public class ProductTableTest extends SpringAbstractSimpleShopAxonTest {

	@Autowired 
	private ProductTableRepository ptRepo;
	
	@Autowired
	private ApplicationCommandGateway cg;
	
	private TimeUnit unit = TimeUnit.SECONDS;
	private long timeout = 10;
	
	@Test
	public void testCreateProduct() throws Exception {
		assertNull(ptRepo.findByProductId("p"));
		cg.sendAndWaitException(new CreateProductCell(new Product("p", 10, new Price(25.0))), timeout, unit);
		ProductTable pt = ptRepo.findByProductId("p");
		assertTrue(pt.getQuantity()==10);
		assertTrue(pt.getPrice()==25.0);
	}
	
	@Test
	public void testChangeProduct() throws Exception {
		cg.sendAndWaitException(new CreateProductCell(new Product("p", 10, new Price(25.0))), timeout, unit);
		ProductTable pt = ptRepo.findByProductId("p");
		assertTrue(pt.getQuantity()==10);
		assertTrue(pt.getPrice()==25.0);
		cg.sendAndWaitException(new RemoveProductFromProductCell(new Product("p", 3, 
				new Price(0.0))), timeout, unit);
		ProductTable pt1 = ptRepo.findByProductId("p");
		assertTrue(pt1.getQuantity()==7);
		assertTrue(pt1.getPrice()==25.0);
	}
}
