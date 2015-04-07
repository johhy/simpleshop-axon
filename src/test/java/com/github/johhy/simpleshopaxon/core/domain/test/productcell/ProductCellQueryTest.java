package com.github.johhy.simpleshopaxon.core.domain.test.productcell;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.johhy.simpleshopaxon.core.commandgateway.ProductCellCommandGateway;
import com.github.johhy.simpleshopaxon.core.commands.productcell.AddProductToProductCellCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.ChangePriceOfProductCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.CreateProductCellCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.GiveAmountProductFromProductCellForOrderCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.RemoveProductCellCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.RemoveProductFromProductCellCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.ReturnAmountProductFromOrderToProductCellCommand;
import com.github.johhy.simpleshopaxon.query.productcell.ProductCellRepository;
import com.github.johhy.simpleshopaxon.query.productcell.ReservedProductRepository;
import com.github.johhy.simpleshopaxon.test.SpringAbstractSimpleShopAxonTest;
/**
 * 
 * @author johhy
 *
 */
public class ProductCellQueryTest extends SpringAbstractSimpleShopAxonTest {
	
	@Autowired
	private ProductCellCommandGateway productCG;
	
	@Autowired
	private ProductCellRepository queryRepo;
	
	@Autowired
	private ReservedProductRepository queryRPRepo;
	
	@Test
	public void testCreateProductCell() throws Exception {
		assertTrue(0==queryRepo.count());
		productCG.createProductCell(new CreateProductCellCommand("a"));
		assertTrue(1==queryRepo.count());
		assertNotNull(queryRepo.findByCodeOfProduct("a"));
		assertTrue(0==queryRepo.findByCodeOfProduct("a").getQuantity());
		assertTrue(0==queryRepo.findByCodeOfProduct("a").getPrice());
	}
	
	@Test
	public void testAddProductToProductCell() throws Exception {
		productCG.createProductCell(new CreateProductCellCommand("a"));
		productCG.addAmountProductToProductCell(
				new AddProductToProductCellCommand("a", 50));
		assertTrue(50==queryRepo.findByCodeOfProduct("a").getQuantity());
		productCG.addAmountProductToProductCell(
				new AddProductToProductCellCommand("a", 25));
		assertTrue(75==queryRepo.findByCodeOfProduct("a").getQuantity());
	}
	
	@Test
	public void testRemoveProductFromProductCell() throws Exception {
		productCG.createProductCell(new CreateProductCellCommand("a"));
		productCG.addAmountProductToProductCell(
				new AddProductToProductCellCommand("a", 50));
		assertTrue(50==queryRepo.findByCodeOfProduct("a").getQuantity());
		productCG.removeAmountProductFromProductCell(
				new RemoveProductFromProductCellCommand("a", 15));
		assertTrue(35==queryRepo.findByCodeOfProduct("a").getQuantity());
	}
	
	@Test
	public void testChangePriceInProductCell() throws Exception {
		productCG.createProductCell(new CreateProductCellCommand("a"));
		productCG.changePriceOfProductInProductCell(
				new ChangePriceOfProductCommand("a", 130));
		assertTrue(130==queryRepo.findByCodeOfProduct("a").getPrice());
		productCG.changePriceOfProductInProductCell(
				new ChangePriceOfProductCommand("a", 100));
		assertTrue(100==queryRepo.findByCodeOfProduct("a").getPrice());
	}
	
	@Test
	public void testGiveAmountProductFromProductCellForOrder() throws Exception {
		productCG.createProductCell(new CreateProductCellCommand("a"));
		productCG.addAmountProductToProductCell(
				new AddProductToProductCellCommand("a", 50));
		assertTrue(50==queryRepo.findByCodeOfProduct("a").getQuantity());
		productCG.giveAmountProductFromProductCellForOrder(new 
				GiveAmountProductFromProductCellForOrderCommand("a", "order", 5));
		assertTrue(45==queryRepo.findByCodeOfProduct("a").getQuantity());
		assertTrue(5==queryRPRepo.findByOrderIdAndCodeOfProduct("order", "a").getAmount());
	}
	
	@Test
	public void testReturnAmountProductFromOrderToProductCell() throws Exception {
		productCG.createProductCell(new CreateProductCellCommand("a"));
		productCG.addAmountProductToProductCell(
				new AddProductToProductCellCommand("a", 50));
		assertTrue(50==queryRepo.findByCodeOfProduct("a").getQuantity());
		productCG.giveAmountProductFromProductCellForOrder(new 
				GiveAmountProductFromProductCellForOrderCommand("a", "order", 5));
		productCG.returnAmountProductFromOrderToProductCell(
				new ReturnAmountProductFromOrderToProductCellCommand("a", "order", 3));
		assertTrue(48==queryRepo.findByCodeOfProduct("a").getQuantity());
		assertTrue(2==queryRPRepo.findByOrderIdAndCodeOfProduct("order", "a").getAmount());
		productCG.returnAmountProductFromOrderToProductCell(
				new ReturnAmountProductFromOrderToProductCellCommand("a", "order", 2));
		assertTrue(50==queryRepo.findByCodeOfProduct("a").getQuantity());
		assertNull(queryRPRepo.findByOrderIdAndCodeOfProduct("order", "a"));
	}
	
	@Test
	public void testRemoveProductCell() throws Exception {
		productCG.createProductCell(new CreateProductCellCommand("a"));
		assertNotNull(queryRepo.findByCodeOfProduct("a"));
		productCG.removeProductCell(new RemoveProductCellCommand("a"));
		assertNull(queryRepo.findByCodeOfProduct("a"));
	}
}
