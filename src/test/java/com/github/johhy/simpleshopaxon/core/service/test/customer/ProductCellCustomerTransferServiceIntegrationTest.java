package com.github.johhy.simpleshopaxon.core.service.test.customer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.johhy.simpleshopaxon.core.commandgateway.CustomerCommandGateway;
import com.github.johhy.simpleshopaxon.core.commandgateway.ProductCellCommandGateway;
import com.github.johhy.simpleshopaxon.core.commandgateway.ProductCellCustomerTransferServiceCommandGateway;
import com.github.johhy.simpleshopaxon.core.commands.customer.CreateCustomerCommand;
import com.github.johhy.simpleshopaxon.core.commands.customer.CreateOrderCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.AddProductToProductCellCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.ChangePriceOfProductCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.CreateProductCellCommand;
import com.github.johhy.simpleshopaxon.core.service.transfer.TransferProductFromCustomerToProductCellCommand;
import com.github.johhy.simpleshopaxon.core.service.transfer.TransferProductFromProductCellToCustomerCommand;
import com.github.johhy.simpleshopaxon.query.customer.CustomerRepository;
import com.github.johhy.simpleshopaxon.query.customer.CustomerTable;
import com.github.johhy.simpleshopaxon.query.productcell.ProductCellRepository;
import com.github.johhy.simpleshopaxon.query.productcell.ProductCellTable;
import com.github.johhy.simpleshopaxon.query.productcell.ReservedProductRepository;
import com.github.johhy.simpleshopaxon.test.SpringAbstractSimpleShopAxonTest;

/**
 * @author johhy
 *
 */
public class ProductCellCustomerTransferServiceIntegrationTest 
	extends SpringAbstractSimpleShopAxonTest {
	
	@Autowired
	private ProductCellCustomerTransferServiceCommandGateway transferCG;
	
	@Autowired
	private CustomerCommandGateway customerCG;
	
	@Autowired
	private ProductCellCommandGateway productCG;
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private ProductCellRepository productRepo;
	
	@Autowired
	private ReservedProductRepository reservedRepo;
	
	
	@Before
	public void setup() throws Exception {
		productCG.createProductCell(new CreateProductCellCommand("codeOfProduct"));
		productCG.addAmountProductToProductCell(
				new AddProductToProductCellCommand("codeOfProduct", 50));
		productCG.changePriceOfProductInProductCell(new ChangePriceOfProductCommand("codeOfProduct", 100));
		customerCG.createCustomer(new CreateCustomerCommand("customer", "customerName", "customerLogin"));
		customerCG.createOrder(new CreateOrderCommand("customer", "order"));
	}
	
	@Test
	public void testTransferProductFromProductCellToCustomer() throws Exception {
		ProductCellTable pct = productRepo.findByCodeOfProduct("codeOfProduct");
		assertTrue(50==pct.getQuantity());
		assertTrue(100==pct.getPrice());
		assertNull(reservedRepo.findByOrderIdAndCodeOfProduct("order", "codeOfProduct"));
		assertNull(customerRepo
				.findByCustomerIdAndOrderIdAndCodeOfProduct("customer", "order", "codeOfProduct"));
		
		transferCG.transferProductFromProductCellToCustomerCommand(
				new TransferProductFromProductCellToCustomerCommand(
						"codeOfProduct", 5, 100, "customer", "order"));
		
		ProductCellTable pctAfter = productRepo.findByCodeOfProduct("codeOfProduct");
		assertTrue(45==pctAfter.getQuantity());
		assertTrue(100==pctAfter.getPrice());
		assertTrue(5==reservedRepo.findByOrderIdAndCodeOfProduct("order", "codeOfProduct").getAmount());
		CustomerTable ct =customerRepo
				.findByCustomerIdAndOrderIdAndCodeOfProduct("customer", "order", "codeOfProduct");
		assertTrue(5==ct.getQuantity());
		assertTrue(100==ct.getPrice());		
	}
	
	@Test
	public void testTransferProductFromProductCellToCustomerWithTransferServiceException() throws Exception {
		ProductCellTable pct = productRepo.findByCodeOfProduct("codeOfProduct");
		assertTrue(50==pct.getQuantity());
		assertTrue(100==pct.getPrice());
		assertNull(reservedRepo.findByOrderIdAndCodeOfProduct("order", "codeOfProduct"));
		assertNull(customerRepo
				.findByCustomerIdAndOrderIdAndCodeOfProduct("customer", "order", "codeOfProduct"));
		//check exception in product cell
		try {
			transferCG.transferProductFromProductCellToCustomerCommand(
					new TransferProductFromProductCellToCustomerCommand(
							"codeOfProduct", 1000, 100, "customer", "order"));
			fail("Must be exception");
		} catch (Exception e) {}
		//no change must be
		ProductCellTable pctAfter1 = productRepo.findByCodeOfProduct("codeOfProduct");
		assertTrue(50==pctAfter1.getQuantity());
		assertTrue(100==pctAfter1.getPrice());
		assertNull(reservedRepo.findByOrderIdAndCodeOfProduct("order", "codeOfProduct"));
		assertNull(customerRepo
				.findByCustomerIdAndOrderIdAndCodeOfProduct("customer", "order", "codeOfProduct"));
		//check exception in second operation of service in customer 
		try {
			transferCG.transferProductFromProductCellToCustomerCommand(
					new TransferProductFromProductCellToCustomerCommand(
							"codeOfProduct", 10, 100, "customer", "noExistsOrder"));
			fail("Must be exception");
		} catch (Exception e) {}
		//no change must be
		ProductCellTable pctAfter2 = productRepo.findByCodeOfProduct("codeOfProduct");
		assertTrue(50==pctAfter2.getQuantity());
		assertTrue(100==pctAfter2.getPrice());
		assertNull(reservedRepo.findByOrderIdAndCodeOfProduct("order", "codeOfProduct"));
		assertNull(customerRepo
				.findByCustomerIdAndOrderIdAndCodeOfProduct("customer", "order", "codeOfProduct"));
	}
	
	@Test
	public void testTransferProductFromCustomerToProductCell() throws Exception {
		transferCG.transferProductFromProductCellToCustomerCommand(
				new TransferProductFromProductCellToCustomerCommand(
						"codeOfProduct", 5, 100, "customer", "order"));
		
		ProductCellTable pct = productRepo.findByCodeOfProduct("codeOfProduct");
		assertTrue(45==pct.getQuantity());
		assertTrue(100==pct.getPrice());
		assertTrue(5==reservedRepo.findByOrderIdAndCodeOfProduct("order", "codeOfProduct").getAmount());
		CustomerTable ct =customerRepo
				.findByCustomerIdAndOrderIdAndCodeOfProduct("customer", "order", "codeOfProduct");
		assertTrue(5==ct.getQuantity());
		assertTrue(100==ct.getPrice());		
		
		transferCG.transferProductFromCustomerToProductCellCommand(
				new TransferProductFromCustomerToProductCellCommand(
						"codeOfProduct", 5, 100, "customer", "order"));
		ProductCellTable pctAfter = productRepo.findByCodeOfProduct("codeOfProduct");
		assertTrue(50==pctAfter.getQuantity());
		assertTrue(100==pctAfter.getPrice());
		assertNull(reservedRepo.findByOrderIdAndCodeOfProduct("order", "codeOfProduct"));
		assertNull(customerRepo
				.findByCustomerIdAndOrderIdAndCodeOfProduct("customer", "order", "codeOfProduct"));		
		
	}
	
	@Test
	public void testTransferProductFromCustomerToProductCellWithTransferServiceException() 
			throws Exception {
		transferCG.transferProductFromProductCellToCustomerCommand(
				new TransferProductFromProductCellToCustomerCommand(
						"codeOfProduct", 5, 100, "customer", "order"));
		ProductCellTable pct = productRepo.findByCodeOfProduct("codeOfProduct");
		assertTrue(45==pct.getQuantity());
		assertTrue(100==pct.getPrice());
		assertTrue(5==reservedRepo.findByOrderIdAndCodeOfProduct("order", "codeOfProduct").getAmount());
		CustomerTable ct =customerRepo
				.findByCustomerIdAndOrderIdAndCodeOfProduct("customer", "order", "codeOfProduct");
		assertTrue(5==ct.getQuantity());
		assertTrue(100==ct.getPrice());		
		
		try {
			transferCG.transferProductFromCustomerToProductCellCommand(
				new TransferProductFromCustomerToProductCellCommand(
						"noExistsCodeOfProduct", 5, 100, "customer", "order"));
			fail("Must be exception");
		} catch (Exception e) {}
		//no change must be
		ProductCellTable pctAfter1 = productRepo.findByCodeOfProduct("codeOfProduct");
		assertTrue(45==pctAfter1.getQuantity());
		assertTrue(100==pctAfter1.getPrice());
		assertTrue(5==reservedRepo.findByOrderIdAndCodeOfProduct("order", "codeOfProduct").getAmount());
		CustomerTable ctAfter1 =customerRepo
				.findByCustomerIdAndOrderIdAndCodeOfProduct("customer", "order", "codeOfProduct");
		assertTrue(5==ctAfter1.getQuantity());
		assertTrue(100==ctAfter1.getPrice());	
		
		try {
			transferCG.transferProductFromCustomerToProductCellCommand(
				new TransferProductFromCustomerToProductCellCommand(
						"codeOfProduct", 5000, 100, "customer", "order"));
			fail("Must be exception");
		} catch (Exception e) {}
		//no change must be
		ProductCellTable pctAfter2 = productRepo.findByCodeOfProduct("codeOfProduct");
		assertTrue(45==pctAfter2.getQuantity());
		assertTrue(100==pctAfter2.getPrice());
		assertTrue(5==reservedRepo.findByOrderIdAndCodeOfProduct("order", "codeOfProduct").getAmount());
		CustomerTable ctAfter2 =customerRepo
				.findByCustomerIdAndOrderIdAndCodeOfProduct("customer", "order", "codeOfProduct");
		assertTrue(5==ctAfter2.getQuantity());
		assertTrue(100==ctAfter2.getPrice());	
	}
}
