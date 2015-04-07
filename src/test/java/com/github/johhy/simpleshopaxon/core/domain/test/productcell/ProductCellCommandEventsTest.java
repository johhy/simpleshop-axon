package com.github.johhy.simpleshopaxon.core.domain.test.productcell;

import org.axonframework.repository.AggregateNotFoundException;
import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

import com.github.johhy.simpleshopaxon.core.commands.productcell.AddProductToProductCellCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.ChangePriceOfProductCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.CreateProductCellCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.GiveAmountProductFromProductCellForOrderCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.RemoveProductCellCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.RemoveProductFromProductCellCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.ReturnAmountProductFromOrderToProductCellCommand;
import com.github.johhy.simpleshopaxon.core.domain.productcell.ProductCell;
import com.github.johhy.simpleshopaxon.core.domain.productcell.ProductCellAggregateFactory;
import com.github.johhy.simpleshopaxon.core.domain.productcell.ProductCellLogicaImpl;
import com.github.johhy.simpleshopaxon.core.events.productcell.PriceProductChangedEvent;
import com.github.johhy.simpleshopaxon.core.events.productcell.ProductCellCreatedEvent;
import com.github.johhy.simpleshopaxon.core.events.productcell.ProductCellRemovedEvent;
import com.github.johhy.simpleshopaxon.core.events.productcell.ProductQuantityChangedInProductCellEvent;
import com.github.johhy.simpleshopaxon.core.events.productcell.ReservedProductChangedInProductCellEvent;
import com.github.johhy.simpleshopaxon.core.exceptions.customer.OrderNotFoundException;
import com.github.johhy.simpleshopaxon.core.exceptions.productcell.ProductInProductCellLessThanNeedException;
import com.github.johhy.simpleshopaxon.core.exceptions.productcell.RemoveProductLessThanNeedException;
import com.github.johhy.simpleshopaxon.core.exceptions.productcell.ReservedProductInProductCellLessThanReturnedFromOrderException;
import com.github.johhy.simpleshopaxon.core.exceptions.productcell.ReservedProductsExistsInProductCellException;
import com.github.johhy.simpleshopaxon.test.AbstractSimpleShopAxonTest;
/**
 * 
 * @author johhy
 *
 */
public class ProductCellCommandEventsTest extends AbstractSimpleShopAxonTest {
	private FixtureConfiguration<ProductCell> fixture;
	

	@Before
	public void setUp() throws Exception {
		fixture = Fixtures.newGivenWhenThenFixture(ProductCell.class);
		fixture.registerAggregateFactory(
				new ProductCellAggregateFactory(ProductCellLogicaImpl.class));
	}
	
	@Test
	public void testAggregateNotFoundException() throws Exception {
		fixture.given()
			.when(new AddProductToProductCellCommand("a", 30))
			.expectException(AggregateNotFoundException.class);
	}
	
	@Test
	public void testCreateProductCell() throws Exception {
		fixture.given()
			.when(new CreateProductCellCommand("a"))
			.expectEvents(new ProductCellCreatedEvent("a"));
	}
	
	@Test
	public void testAddProductToProductCell() throws Exception {
		fixture.given(new ProductCellCreatedEvent("a"))
			.when(new AddProductToProductCellCommand("a", 30))
			.expectEvents(new ProductQuantityChangedInProductCellEvent("a", 30));
	}
	
	@Test
	public void testRemoveProductLessThanNeenException() {
		fixture.given(new ProductCellCreatedEvent("a"))
		.when(new RemoveProductFromProductCellCommand("a", 100))
		.expectException(RemoveProductLessThanNeedException.class);
	}
	
	@Test
	public void testRemoveProductFromProductCell() throws Exception {
		fixture.given(new ProductCellCreatedEvent("a"), 
				new ProductQuantityChangedInProductCellEvent("a", 50))
			.when(new RemoveProductFromProductCellCommand("a", 10))
			.expectEvents(new ProductQuantityChangedInProductCellEvent("a", 40));
	}
	
	@Test
	public void testChangePriceProduct() throws Exception {
		fixture.given(new ProductCellCreatedEvent("a"))
			.when(new ChangePriceOfProductCommand("a", 30))
			.expectEvents(new PriceProductChangedEvent("a", 30));
	}
	
	@Test
	public void testProductInProductCellLessThanNeedException() throws Exception {
		fixture.given(new ProductCellCreatedEvent("a"),
				new ProductQuantityChangedInProductCellEvent("a", 50))
		.when(new GiveAmountProductFromProductCellForOrderCommand("a", "order", 100))
		.expectException(ProductInProductCellLessThanNeedException.class);
	}
	
	@Test
	public void testGiveAmountProductFromProductCellForOrder() throws Exception {
		fixture.given(new ProductCellCreatedEvent("a"),
				new ProductQuantityChangedInProductCellEvent("a", 50))
			.when(new GiveAmountProductFromProductCellForOrderCommand("a", "order", 10))
			.expectEvents(new ProductQuantityChangedInProductCellEvent("a", 40), 
					new ReservedProductChangedInProductCellEvent("a", "order", 10));
	}
	
	@Test
	public void testOrderNoFoundException() throws Exception {
		fixture.given(new ProductCellCreatedEvent("a"),
				new ProductQuantityChangedInProductCellEvent("a", 50))
			.when(new ReturnAmountProductFromOrderToProductCellCommand("a", "order", 5))
			.expectException(OrderNotFoundException.class);
	}
	
	@Test
	public void testReservedProductInProductCellLessThanReturnedFromOrderException()
		throws Exception {
		fixture.given(new ProductCellCreatedEvent("a"),
				new ProductQuantityChangedInProductCellEvent("a", 50),
				new ProductQuantityChangedInProductCellEvent("a", 10),
				new ReservedProductChangedInProductCellEvent("a", "order", 10))
			.when(new ReturnAmountProductFromOrderToProductCellCommand("a", "order", 15))
			.expectException(
					ReservedProductInProductCellLessThanReturnedFromOrderException.class);
	}
	
	@Test
	public void testReturnAmountProductFromOrderToProductCell() throws Exception {
		fixture.given(new ProductCellCreatedEvent("a"),
				new ProductQuantityChangedInProductCellEvent("a", 50),
				new ProductQuantityChangedInProductCellEvent("a", 40),
				new ReservedProductChangedInProductCellEvent("a", "order", 10))
			.when(new ReturnAmountProductFromOrderToProductCellCommand("a", "order", 3))
			.expectEvents(new ProductQuantityChangedInProductCellEvent("a", 43),
					new ReservedProductChangedInProductCellEvent("a", "order",7));
	}
	
	@Test
	public void testReservedProductsExistsException() throws Exception {
		fixture.given(new ProductCellCreatedEvent("a"),
				new ProductQuantityChangedInProductCellEvent("a", 50),
				new ProductQuantityChangedInProductCellEvent("a", 40),
				new ReservedProductChangedInProductCellEvent("a", "order", 10))
			.when(new RemoveProductCellCommand("a"))
			.expectException(ReservedProductsExistsInProductCellException.class);
	}
	
	@Test
	public void testRemoveProductCell() throws Exception {
		fixture.given(new ProductCellCreatedEvent("a"))
			.when(new RemoveProductCellCommand("a"))
			.expectEvents(new ProductCellRemovedEvent("a"));
	}
	
	@Test
	public void testChangePriceRemovedProductCellThrowException() {
		fixture.given(new ProductCellCreatedEvent("a"),
				new ProductCellRemovedEvent("a"))
				.when(new ChangePriceOfProductCommand("a", 100))
				.expectException(AggregateNotFoundException.class);
			
	}

}