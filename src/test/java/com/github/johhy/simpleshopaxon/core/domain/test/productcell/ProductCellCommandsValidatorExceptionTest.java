package com.github.johhy.simpleshopaxon.core.domain.test.productcell;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.johhy.simpleshopaxon.core.commandgateway.ProductCellCommandGateway;
import com.github.johhy.simpleshopaxon.core.commands.productcell.AddProductToProductCellCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.CreateProductCellCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.GiveAmountProductFromProductCellForOrderCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.RemoveProductCellCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.RemoveProductFromProductCellCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.ReturnAmountProductFromOrderToProductCellCommand;
import com.github.johhy.simpleshopaxon.core.exceptions.customer.OrderNotFoundException;
import com.github.johhy.simpleshopaxon.core.exceptions.productcell.ProductCellExistsException;
import com.github.johhy.simpleshopaxon.core.exceptions.productcell.ProductCellNoFoundException;
import com.github.johhy.simpleshopaxon.core.exceptions.productcell.ProductInProductCellLessThanNeedException;
import com.github.johhy.simpleshopaxon.core.exceptions.productcell.RemoveProductLessThanNeedException;
import com.github.johhy.simpleshopaxon.core.exceptions.productcell.ReservedProductInProductCellLessThanReturnedFromOrderException;
import com.github.johhy.simpleshopaxon.core.exceptions.productcell.ReservedProductsExistsInProductCellException;
import com.github.johhy.simpleshopaxon.query.productcell.ProductCellRepository;
import com.github.johhy.simpleshopaxon.query.productcell.ReservedProductRepository;
import com.github.johhy.simpleshopaxon.test.SpringAbstractSimpleShopAxonTest;

/**
 * 
 * @author johhy
 *
 */
public class ProductCellCommandsValidatorExceptionTest extends SpringAbstractSimpleShopAxonTest {

	@Autowired
	private ProductCellCommandGateway productCG;
	
	@Autowired
	private ProductCellRepository queryRepo;
	
	@Autowired
	private ReservedProductRepository queryRPRepo;
	
	@Test
	public void testRemoveProductLessThanNeenException() throws Exception {
		productCG.createProductCell(new CreateProductCellCommand("a"));
		productCG.addAmountProductToProductCell(new AddProductToProductCellCommand("a", 15));
		try {
			productCG.removeAmountProductFromProductCell(new RemoveProductFromProductCellCommand("a", 20));
			fail("must be throws exception");
		} catch (RemoveProductLessThanNeedException e) {}
	}
	
	@Test
	public void testProductInProductCellLessThanNeedException() throws Exception {
		productCG.createProductCell(new CreateProductCellCommand("a"));
		productCG.addAmountProductToProductCell(new AddProductToProductCellCommand("a", 15));
		try {
		    productCG.giveAmountProductFromProductCellForOrder(
				new GiveAmountProductFromProductCellForOrderCommand("a", "orderId", 20));
		    	fail("must be throws exception");
		} catch (ProductInProductCellLessThanNeedException e) {}
	}
	
	
	@Test
	public void testOrderNoFoundException() throws Exception {
		productCG.createProductCell(new CreateProductCellCommand("a"));
		try {
			productCG.returnAmountProductFromOrderToProductCell(
				new ReturnAmountProductFromOrderToProductCellCommand("a", "orderId", 15));
			fail("must be throws exception");
		} catch(OrderNotFoundException e) {}
	}
	
	@Test
	public void testReservedProductInProductCellLessThanReturnedFromOrderException()
		throws Exception {
		productCG.createProductCell(new CreateProductCellCommand("a"));
		productCG.addAmountProductToProductCell(new AddProductToProductCellCommand("a", 20));
		productCG.giveAmountProductFromProductCellForOrder(
				new GiveAmountProductFromProductCellForOrderCommand("a", "orderId", 10));
		try {
			productCG.returnAmountProductFromOrderToProductCell(
					new ReturnAmountProductFromOrderToProductCellCommand("a", "orderId", 15));
			fail("must be throws exception");
		} catch(ReservedProductInProductCellLessThanReturnedFromOrderException e) {}
	}
	
	@Test
	public void testReservedProductsExistsException() throws Exception {
		productCG.createProductCell(new CreateProductCellCommand("a"));
		productCG.addAmountProductToProductCell(new AddProductToProductCellCommand("a", 20));
		productCG.giveAmountProductFromProductCellForOrder(
				new GiveAmountProductFromProductCellForOrderCommand("a", "orderId", 10));
		try {
			productCG.removeProductCell(
					new RemoveProductCellCommand("a"));
			fail("must be throws exception");
		} catch(ReservedProductsExistsInProductCellException e) {}
	}
	
	@Test 
	public void testProductCellNoFoundException() throws Exception {
		try {
			productCG.removeProductCell(
				new RemoveProductCellCommand("noExistsProdCell"));
			fail("must be throws exception");
		} catch (ProductCellNoFoundException e) {}
	}
	
	@Test
	public void testProductCellExistsException() throws Exception {
		productCG.createProductCell(
				new CreateProductCellCommand("a"));
		try {
			productCG.createProductCell(
					new CreateProductCellCommand("a"));
			fail("must be throws exception");
		} catch (ProductCellExistsException e) {}
	}
	
}
