package com.github.johhy.simpleshopaxon.core.domain.test.customer;

import org.axonframework.repository.AggregateNotFoundException;
import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

import com.github.johhy.simpleshopaxon.core.commands.customer.AddProductToOrderCommand;
import com.github.johhy.simpleshopaxon.core.commands.customer.ChangeCustomerInfoCommand;
import com.github.johhy.simpleshopaxon.core.commands.customer.CreateCustomerCommand;
import com.github.johhy.simpleshopaxon.core.commands.customer.CreateOrderCommand;
import com.github.johhy.simpleshopaxon.core.commands.customer.DeleteOrderCommand;
import com.github.johhy.simpleshopaxon.core.commands.customer.RemoveCustomerCommand;
import com.github.johhy.simpleshopaxon.core.commands.customer.RemoveProductFromOrderCommand;
import com.github.johhy.simpleshopaxon.core.domain.customer.Customer;
import com.github.johhy.simpleshopaxon.core.domain.customer.CustomerAggregateFactory;
import com.github.johhy.simpleshopaxon.core.domain.customer.CustomerLogicaImpl;
import com.github.johhy.simpleshopaxon.core.domain.customer.OrderLogicaImpl;
import com.github.johhy.simpleshopaxon.core.events.customer.CustomerCreatedEvent;
import com.github.johhy.simpleshopaxon.core.events.customer.CustomerInfoChangedEvent;
import com.github.johhy.simpleshopaxon.core.events.customer.CustomerRemovedEvent;
import com.github.johhy.simpleshopaxon.core.events.customer.OrderCreatedEvent;
import com.github.johhy.simpleshopaxon.core.events.customer.OrderDeletedEvent;
import com.github.johhy.simpleshopaxon.core.events.customer.ProductAddedToOrderEvent;
import com.github.johhy.simpleshopaxon.core.events.customer.ProductRemovedFromOrderEvent;
import com.github.johhy.simpleshopaxon.core.exceptions.customer.AmountProductToRemoveNotEqualInOrderException;
import com.github.johhy.simpleshopaxon.core.exceptions.customer.OrderNotEmptyException;
import com.github.johhy.simpleshopaxon.core.exceptions.customer.OrderNotFoundException;
import com.github.johhy.simpleshopaxon.core.exceptions.customer.ProductExistsException;
import com.github.johhy.simpleshopaxon.core.exceptions.customer.ProductNotFoundException;
import com.github.johhy.simpleshopaxon.test.AbstractSimpleShopAxonTest;

/**
 * @author johhy
 *
 */
public class CustomerCommandEventsTest extends AbstractSimpleShopAxonTest {
	
	private FixtureConfiguration<Customer> fixture;
	
	@Before
	public void setUp() throws Exception {
		fixture = Fixtures.newGivenWhenThenFixture(Customer.class);
		fixture.registerAggregateFactory(
				new CustomerAggregateFactory(
						CustomerLogicaImpl.class, OrderLogicaImpl.class));
	}
	
	@Test
	public void testAggregateNotFoundException() throws Exception {
		fixture.given()
			.when(new CreateOrderCommand("1", "order"))
			.expectException(AggregateNotFoundException.class);
	}
	
	@Test
	public void testCreateCustomer() throws Exception {
		fixture.given()
			.when(new CreateCustomerCommand("1", "customerName", "customerLogin"))
			.expectEvents(new CustomerCreatedEvent("1", "customerName", "customerLogin"));
	}
	
	@Test
	public void testRemoveCustomer() throws Exception {
		fixture.given(new CustomerCreatedEvent("1", "customerName", "customerLogin"))
			.when(new RemoveCustomerCommand("1"))
			.expectEvents(new CustomerRemovedEvent("1"));
	}
	
	@Test
	public void testChangeCustomerInfo() throws Exception {
		fixture.given(new CustomerCreatedEvent("1", "customerName", "customerLogin"))
			.when(new ChangeCustomerInfoCommand("1", "newName", "newLogin"))
			.expectEvents(new CustomerInfoChangedEvent("1", "newName", "newLogin"));
	}
	
	@Test
	public void testCreateOrder() throws Exception {
		fixture.given(new CustomerCreatedEvent("1", "customerName", "customerLogin"))
			.when(new CreateOrderCommand("1", "order"))
			.expectEvents(new OrderCreatedEvent("1", "order"));
	}
	
	@Test
	public void testDeleteOrder() throws Exception {
		fixture.given(new CustomerCreatedEvent("1", "customerName", "customerLogin"),
				new OrderCreatedEvent("1", "order"))
			.when(new DeleteOrderCommand("1", "order"))
			.expectEvents(new OrderDeletedEvent("1", "order"));
	}
	
	@Test
	public void testDeleteOrderWithOrderNotFoundException() throws Exception {
		fixture.given(new CustomerCreatedEvent("1", "customerName", "customerLogin"))
			.when(new DeleteOrderCommand("1", "order"))
			.expectException(OrderNotFoundException.class);
	}
	
	@Test
	public void testDeleteOrderWithOrderNotEmptyException() throws Exception {
		fixture.given(new CustomerCreatedEvent("1", "customerName", "customerLogin"),
				new OrderCreatedEvent("1", "order"),
				new ProductAddedToOrderEvent("1", "order", 
						"codeOfProduct", 5, 10))
			.when(new DeleteOrderCommand("1", "order"))
			.expectException(OrderNotEmptyException.class);
	}
	
	@Test
	public void testAddProductToOrderCommand() throws Exception {
		fixture.given(new CustomerCreatedEvent("1", "customerName", "customerLogin"),
				new OrderCreatedEvent("1", "order"))
			.when(new AddProductToOrderCommand("1", "order", "codeOfProduct",
						5, 10))
			.expectEvents(new ProductAddedToOrderEvent("1", "order", 
						"codeOfProduct", 5, 10));
	}
	
	@Test
	public void testAddProductToOrderWithProductExistsException() throws Exception {
		fixture.given(new CustomerCreatedEvent("1", "customerName", "customerLogin"),
				new OrderCreatedEvent("1", "order"),
				new ProductAddedToOrderEvent("1", "order", 
						"codeOfProduct", 5, 10))
			.when(new AddProductToOrderCommand("1", "order", "codeOfProduct",
						34, 12))
			.expectException(ProductExistsException.class);
	}
	
	@Test
	public void testAddProductToOrderWithOrderNotFoundException() throws Exception {
		fixture.given(new CustomerCreatedEvent("1", "customerName", "customerLogin"),
				new OrderCreatedEvent("1", "order"),
				new ProductAddedToOrderEvent("1", "order", 
						"codeOfProduct", 5, 10))
			.when(new AddProductToOrderCommand("1", "noOrderExists", "codeOfProduct",
						34, 12))
			.expectException(IllegalStateException.class);
	}
	
	@Test
	public void testRemoveProductFromOrderCommand() throws Exception {
		fixture.given(new CustomerCreatedEvent("1", "customerName", "customerLogin"),
				new OrderCreatedEvent("1", "order"),
				new ProductAddedToOrderEvent("1", "order", 
						"codeOfProduct", 5, 10))
			.when(new RemoveProductFromOrderCommand("1", "order", "codeOfProduct", 5))
			.expectEvents(new ProductRemovedFromOrderEvent("1", "order",
					"codeOfProduct"));
	}
	
	@Test
	public void testRemoveProductFromOrderWithProductNotFoundException() throws Exception {
		fixture.given(new CustomerCreatedEvent("1", "customerName", "customerLogin"),
				new OrderCreatedEvent("1", "order"),
				new ProductAddedToOrderEvent("1", "order", 
						"codeOfProduct", 5, 10))
			.when(new RemoveProductFromOrderCommand("1", "order", "noProductExists", 5))
			.expectException(ProductNotFoundException.class);
	}
	
	@Test
	public void testRemoveProductFromOrderWithOrderNotFoundException() throws Exception {
		fixture.given(new CustomerCreatedEvent("1", "customerName", "customerLogin"),
				new OrderCreatedEvent("1", "order"),
				new ProductAddedToOrderEvent("1", "order", 
						"codeOfProduct", 5, 10))
			.when(new RemoveProductFromOrderCommand("1", "noOrderExist", "codeOfProduct", 5))
			.expectException(IllegalStateException.class);
	}
	
	@Test
	public void testRemoveProductFromOrderWithAmountProductToRemoveNoEqualInOrderException() 
			throws Exception {
		fixture.given(new CustomerCreatedEvent("1", "customerName", "customerLogin"),
				new OrderCreatedEvent("1", "order"),
				new ProductAddedToOrderEvent("1", "order", 
						"codeOfProduct", 5, 10))
			.when(new RemoveProductFromOrderCommand("1", "order", "codeOfProduct", 50))
			.expectException(AmountProductToRemoveNotEqualInOrderException.class);
	}
}
