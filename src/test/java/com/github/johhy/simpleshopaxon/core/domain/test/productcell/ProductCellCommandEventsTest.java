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
import com.github.johhy.simpleshopaxon.core.events.productcell.PriceProductChangedEvent;
import com.github.johhy.simpleshopaxon.core.events.productcell.ProductCellCreatedEvent;
import com.github.johhy.simpleshopaxon.core.events.productcell.ProductCellRemovedEvent;
import com.github.johhy.simpleshopaxon.core.events.productcell.ProductQuantityChangedInProductCellEvent;
import com.github.johhy.simpleshopaxon.core.events.productcell.ReservedProductChangedInProductCellEvent;
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
	public void testGiveAmountProductFromProductCellForOrder() throws Exception {
		fixture.given(new ProductCellCreatedEvent("a"),
				new ProductQuantityChangedInProductCellEvent("a", 50))
			.when(new GiveAmountProductFromProductCellForOrderCommand("a", "order", 10))
			.expectEvents(new ProductQuantityChangedInProductCellEvent("a", 40), 
					new ReservedProductChangedInProductCellEvent("a", "order", 10));
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
	public void testRemoveProductCell() throws Exception {
		fixture.given(new ProductCellCreatedEvent("a"))
			.when(new RemoveProductCellCommand("a"))
			.expectEvents(new ProductCellRemovedEvent("a"));
	}
	

}