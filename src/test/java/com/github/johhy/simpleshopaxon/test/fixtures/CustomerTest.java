package com.github.johhy.simpleshopaxon.test.fixtures;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

import com.github.johhy.simpleshopaxon.core.Customer;
import com.github.johhy.simpleshopaxon.core.api.commands.ChangeCustomerAddress;
import com.github.johhy.simpleshopaxon.core.api.commands.ClearShoppingCart;
import com.github.johhy.simpleshopaxon.core.api.commands.CreateCustomer;
import com.github.johhy.simpleshopaxon.core.api.commands.CreateOrderForCustomer;
import com.github.johhy.simpleshopaxon.core.api.events.CustomerAddressChanged;
import com.github.johhy.simpleshopaxon.core.api.events.CustomerCreated;
import com.github.johhy.simpleshopaxon.core.api.events.OrderCreatedForCustomer;
import com.github.johhy.simpleshopaxon.core.api.events.ShoppingCartChanged;
import com.github.johhy.simpleshopaxon.core.api.exception.DomainStateException;
import com.github.johhy.simpleshopaxon.core.api.shared.Address;
import com.github.johhy.simpleshopaxon.core.api.shared.Price;
import com.github.johhy.simpleshopaxon.core.api.shared.Product;
import com.github.johhy.simpleshopaxon.test.AbstractSimpleShopAxonTest;

public class CustomerTest extends AbstractSimpleShopAxonTest {

    private FixtureConfiguration<Customer> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = Fixtures.newGivenWhenThenFixture(Customer.class);
    }
    
    @Test
    public void testCreateCustomer() throws Exception {
    	Date created = new Date();
    	fixture.given()
    		.when(new CreateCustomer("c", new Address("a"), created))
    		.expectEvents(new CustomerCreated("c", new Address("a"), created));
    }
    
    @Test
    public void testChangeCustomerInfo() throws Exception {
       	Date created = new Date();
       	fixture.given(new CustomerCreated("c", new Address("a"), created))
       		.when(new ChangeCustomerAddress("c", new Address("newA")))
       		.expectEvents(new CustomerAddressChanged("c", new Address("newA")));
    }
 
    @Test
    public void testCleareProductFromShoppingCart() throws Exception {
     	Date created = new Date();
     	Product product1 = new Product("1", 15, new Price(10.0));
    	Product product2 = new Product("2", 10, new Price(12.0));
     	List<Product> products = new ArrayList<Product>();
     	products.add(product1);
     	products.add(product2);
       	fixture.given(new CustomerCreated("c", new Address("a"), created),
       			new ShoppingCartChanged("c", products))
       		.when(new ClearShoppingCart("c"))
       		.expectEvents(new ShoppingCartChanged("c", new ArrayList<Product>()));
    }
    
    @Test
    public void testCreateOrderForCustomer() throws Exception {
    	Date created = new Date();
     	Product product1 = new Product("1", 15, new Price(10.0));
    	Product product2 = new Product("2", 10, new Price(12.0));
     	List<Product> products = new ArrayList<Product>();
     	products.add(product1);
     	products.add(product2);
       	fixture.given(new CustomerCreated("c", new Address("a"), created),
       			new ShoppingCartChanged("c", products))
       			.when(new CreateOrderForCustomer("c", "o", created, new Address("a")))
       			.expectEvents(new OrderCreatedForCustomer("c", "o", created,
       					new Address("a"), products, new Price(270.0)));
    }
    
    @Test
    public void testCreateOrderForCustomerEmptyException() throws Exception {
    	Date created = new Date();
    	fixture.given(new CustomerCreated("c", new Address("a"), created))
       			.when(new CreateOrderForCustomer("c", "o", created, new Address("a")))
       			.expectException(DomainStateException.class);
    }
}
