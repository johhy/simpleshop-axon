package com.github.johhy.simpleshopaxon.test.fixtures;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

import com.github.johhy.simpleshopaxon.core.Order;
import com.github.johhy.simpleshopaxon.core.api.commands.ChangeOrderStatus;
import com.github.johhy.simpleshopaxon.core.api.commands.CreateOrder;
import com.github.johhy.simpleshopaxon.core.api.events.OrderCreated;
import com.github.johhy.simpleshopaxon.core.api.events.OrderStatusChanged;
import com.github.johhy.simpleshopaxon.core.api.exception.DomainStateException;
import com.github.johhy.simpleshopaxon.core.api.shared.Address;
import com.github.johhy.simpleshopaxon.core.api.shared.History;
import com.github.johhy.simpleshopaxon.core.api.shared.OrderStatus;
import com.github.johhy.simpleshopaxon.core.api.shared.Price;
import com.github.johhy.simpleshopaxon.core.api.shared.Product;
import com.github.johhy.simpleshopaxon.test.AbstractSimpleShopAxonTest;

public class OrderTest extends AbstractSimpleShopAxonTest {

	   private FixtureConfiguration<Order> fixture;

	    @Before
	    public void setUp() throws Exception {
	        fixture = Fixtures.newGivenWhenThenFixture(Order.class);
	    }

	    @Test
	    public void testCreateOrder() throws Exception {
	    	Date created = new Date();
	    	Address shipTo = new Address("a");
	    	List<Product> products = new ArrayList<Product>();
	    	products.add(new Product("p", 20, new Price(10.0)));
	    	Price total = new Price(200.0);
	    	fixture.given()
	    		.when(new CreateOrder("c", "o", created, shipTo, products, 
	    				total))
	    		.expectEvents(new OrderCreated("c", "o", created, shipTo, 
	    				products, total));
	    }
	    
	    @Test
	    public void testChangeOrderStatus() throws Exception {
	    	Date created = new Date(10000);
	    	Address shipTo = new Address("a");
	    	List<Product> products = new ArrayList<Product>();
	    	products.add(new Product("p", 20, new Price(10.0)));
	    	Price total = new Price(200.0);
	    	History history = new History(new Date(20000), OrderStatus.SHIPPED);
	    	List<History> historyChanged = new ArrayList<History>();
	    	historyChanged.add(new History(created, OrderStatus.CREATED));
	    	historyChanged.add(history);
	    	fixture.given(new OrderCreated("c", "o", created, shipTo,
	    			products, total))
	    			.when(new ChangeOrderStatus("o", history))
	    			.expectEvents(new OrderStatusChanged("o", historyChanged));
	    }
	    
	    @Test
	    public void testChangeOrderStatusClosedException() throws Exception {
	    	Date created = new Date(10000);
	    	Address shipTo = new Address("a");
	    	List<Product> products = new ArrayList<Product>();
	    	products.add(new Product("p", 20, new Price(10.0)));
	    	Price total = new Price(200.0);
	    	List<History> history = new ArrayList<History>();
	    	history.add(new History(created, OrderStatus.CREATED));
	    	history.add(new History(new Date(20000), OrderStatus.CLOSED));
	    	fixture.given(new OrderCreated("c", "o", created, shipTo,
	    			products, total), 
	    			new OrderStatusChanged("o", history))
	    			.when(new ChangeOrderStatus("o", new History(new Date(),
	    					OrderStatus.SHIPPED)))
	    			.expectException(DomainStateException.class);
	    }
	    
	    @Test
	    public void testChangeOrderStatusExistsException() throws Exception {
	    	Date created = new Date(10000);
	    	Address shipTo = new Address("a");
	    	List<Product> products = new ArrayList<Product>();
	    	products.add(new Product("p", 20, new Price(10.0)));
	    	Price total = new Price(200.0);
	    	List<History> history = new ArrayList<History>();
	    	history.add(new History(created, OrderStatus.CREATED));
	    	history.add(new History(new Date(20000), OrderStatus.SHIPPED));
	    	fixture.given(new OrderCreated("c", "o", created, shipTo,
	    			products, total), 
	    			new OrderStatusChanged("o", history))
	    			.when(new ChangeOrderStatus("o", new History(new Date(),
	    					OrderStatus.SHIPPED)))
	    			.expectException(DomainStateException.class);
	    }
	    
	    @Test
	    public void testChangeOrderStatusInPastException() throws Exception {
	    	Date created = new Date(10000);
	    	Address shipTo = new Address("a");
	    	List<Product> products = new ArrayList<Product>();
	    	products.add(new Product("p", 20, new Price(10.0)));
	    	Price total = new Price(200.0);
	    	List<History> history = new ArrayList<History>();
	    	history.add(new History(created, OrderStatus.CREATED));
	    	Date pastDate = new Date(5000);
	    	fixture.given(new OrderCreated("c", "o", created, shipTo,
	    			products, total), 
	    			new OrderStatusChanged("o", history))
	    			.when(new ChangeOrderStatus("o", 
	    					new History(pastDate, OrderStatus.SHIPPED)))
	    			.expectException(DomainStateException.class);
	    }
}
