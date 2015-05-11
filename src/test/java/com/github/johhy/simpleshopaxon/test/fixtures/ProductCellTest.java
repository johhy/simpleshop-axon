package com.github.johhy.simpleshopaxon.test.fixtures;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.axonframework.repository.Repository;
import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

import com.github.johhy.simpleshopaxon.core.Customer;
import com.github.johhy.simpleshopaxon.core.ProductCell;
import com.github.johhy.simpleshopaxon.core.api.commands.AddProductToProductCell;
import com.github.johhy.simpleshopaxon.core.api.commands.ChangeCapacityProductCell;
import com.github.johhy.simpleshopaxon.core.api.commands.ChangePriceInProductCell;
import com.github.johhy.simpleshopaxon.core.api.commands.CreateProductCell;
import com.github.johhy.simpleshopaxon.core.api.commands.GiveProductToCustomer;
import com.github.johhy.simpleshopaxon.core.api.commands.RemoveProductFromProductCell;
import com.github.johhy.simpleshopaxon.core.api.commands.ReturnProductFromCustomer;
import com.github.johhy.simpleshopaxon.core.api.events.CapacityProductCellChanged;
import com.github.johhy.simpleshopaxon.core.api.events.CustomerCreated;
import com.github.johhy.simpleshopaxon.core.api.events.ProductAddedToProductCell;
import com.github.johhy.simpleshopaxon.core.api.events.ProductCellCreated;
import com.github.johhy.simpleshopaxon.core.api.events.ProductGivenToCustomer;
import com.github.johhy.simpleshopaxon.core.api.events.ProductPriceInProductCellChanged;
import com.github.johhy.simpleshopaxon.core.api.events.ProductRemovedFromProductCell;
import com.github.johhy.simpleshopaxon.core.api.events.ProductReturnedFromCustomer;
import com.github.johhy.simpleshopaxon.core.api.exception.DomainStateException;
import com.github.johhy.simpleshopaxon.core.api.shared.Address;
import com.github.johhy.simpleshopaxon.core.api.shared.Price;
import com.github.johhy.simpleshopaxon.core.api.shared.Product;
import com.github.johhy.simpleshopaxon.test.AbstractSimpleShopAxonTest;

public class ProductCellTest extends AbstractSimpleShopAxonTest {

	
    private FixtureConfiguration<ProductCell> fixture;
    
    private FixtureConfiguration<Customer> fixture1;

    @Before
    public void setUp() throws Exception {
        fixture = Fixtures.newGivenWhenThenFixture(ProductCell.class);
      	fixture1 = Fixtures.newGivenWhenThenFixture(Customer.class);
    	fixture1.given(new CustomerCreated("cc", new Address("a"), new Date()));
    	Repository<Customer> cRepo = fixture1.getRepository();
    	fixture.registerInjectableResource(cRepo);
    }
    
    @Test
    public void testCreateProductCell() throws Exception {
    	Product product =
    			new Product("1", 30, new Price(12.0));
    	fixture.given()
    		.when(new CreateProductCell(product))
    		.expectEvents(new ProductCellCreated(product, 30));
    	
    }
    
    @Test
    public void testChangeCapacityProductCell() throws Exception {
    	Product product =
    			new Product("1", 30, new Price(12.0));
    	fixture.given(new ProductCellCreated(product, 30))
    		.when(new ChangeCapacityProductCell(product, 38))
    		.expectEvents(new CapacityProductCellChanged(product,
    				38));
    }
    
    @Test
    public void testChangeCapacityProductCellException() throws Exception {
    	Product product =
    			new Product("1", 30, new Price(12.0));
    	fixture.given(new ProductCellCreated(product, 30))
    		.when(new ChangeCapacityProductCell(product, 15))
    		.expectException(DomainStateException.class);
    }
    
    @Test 
    public void testChangePriceInProductCell() throws Exception {
    	Product productBefore =
    			new Product("1", 30, new Price(12.0));
    	Product productAfter =
    			new Product("1", 30, new Price(10.0));
    	fixture.given(new ProductCellCreated(productBefore, 30))
    		.when(new ChangePriceInProductCell(productAfter))
    		.expectEvents(new ProductPriceInProductCellChanged(productAfter));
    	
    		
    }
    
    @Test
    public void testAddProductToProductCell() throws Exception {
    	Product productBefore =
    			new Product("1", 30, new Price(12.0));
    	Product productToAdd =
    			new Product("1", 14, new Price(12.0));
       	Product productAfter =
    			new Product("1", 44, new Price(12.0));
    	fixture.given(new ProductCellCreated(productBefore, 30),
    			new CapacityProductCellChanged(productBefore, 50))
    		.when(new AddProductToProductCell(productToAdd))
    		.expectEvents(new ProductAddedToProductCell(productAfter));
    }

    @Test
    public void testAddProductToProductCellException() throws Exception {
    	Product productBefore =
    			new Product("1", 30, new Price(12.0));
    	Product productToAdd =
    			new Product("1", 14, new Price(12.0));
    	fixture.given(new ProductCellCreated(productBefore, 30),
    			new CapacityProductCellChanged(productBefore, 10))
    		.when(new AddProductToProductCell(productToAdd))
    		.expectException(DomainStateException.class);
    }
    
    @Test
    public void testRemoveProductFromProductCell() throws Exception {
    	Product productBefore =
    			new Product("1", 30, new Price(12.0));
    	Product productToRemove =
    			new Product("1", 14, new Price(12.0));
       	Product productAfter =
    			new Product("1", 16, new Price(12.0));
    	fixture.given(new ProductCellCreated(productBefore, 30),
    			new CapacityProductCellChanged(productBefore, 50))
    		.when(new RemoveProductFromProductCell(productToRemove))
    		.expectEvents(new ProductRemovedFromProductCell(productAfter));
    }
    
    @Test
    public void testRemoveProductFromProductCellMoreThanIsException() throws Exception {
    	Product productBefore =
    			new Product("1", 30, new Price(12.0));
    	Product productToRemove =
    			new Product("1", 34, new Price(12.0));
    	fixture.given(new ProductCellCreated(productBefore, 30),
    			new CapacityProductCellChanged(productBefore, 50))
    		.when(new RemoveProductFromProductCell(productToRemove))
    		.expectException(DomainStateException.class);
    }
    
    @Test
    public void testGiveProductForCustomer() throws Exception {
    	Product productBefore =
    			new Product("1", 30, new Price(12.0));
    	Product productToGive =
    			new Product("1", 14, new Price(12.0));
       	Product productAfter =
    			new Product("1", 16, new Price(12.0));
       	Map<String, Integer> reserved = new HashMap<String, Integer>();
       	reserved.put("cc", 14);
    	fixture.given(new ProductCellCreated(productBefore, 30))
    			.when(new GiveProductToCustomer(productToGive, "cc"))
    			.expectEvents(new ProductGivenToCustomer(
    					productAfter, "cc", reserved, productToGive));
    }
    
    @Test
    public void testGiveProductForCustomerMoreThanIsException() throws Exception {
    	Product productBefore =
    			new Product("1", 30, new Price(12.0));
    	Product productToGive =
    			new Product("1", 46, new Price(12.0));
    	fixture.given(new ProductCellCreated(productBefore, 30))
    			.when(new GiveProductToCustomer(productToGive, "cc"))
    			.expectException(DomainStateException.class);
    }
 
    @Test
    public void testReturnProductFromCustomerEqualGiven() throws Exception {
    	Product productBefore =
    			new Product("1", 50, new Price(12.0));
    	Product product =
    			new Product("1", 35, new Price(12.0));
    	Product productGiven =
    			new Product("1", 15, new Price(12.0));
    	Product productReturn =
    			new Product("1", 15, new Price(12.0));
       	Map<String, Integer> reserved = new HashMap<String, Integer>();
       	reserved.put("cc", 15);
    	fixture.given(new ProductCellCreated(productBefore, 50),
    			new ProductGivenToCustomer(product, "cc", reserved, productGiven))
    			.when(new ReturnProductFromCustomer(productReturn, "cc"))
    			.expectEvents(new ProductReturnedFromCustomer(
    					productBefore, "cc", new HashMap<String, Integer>()));
 
    }
    
    @Test
    public void testReturnProductFromCustomerNotEqualAdded() throws Exception {
    	Product productBefore =
    			new Product("1", 50, new Price(12.0));
    	Product product =
    			new Product("1", 35, new Price(12.0));
    	Product productGiven =
    			new Product("1", 15, new Price(12.0));
    	Product productReturn =
    			new Product("1", 10, new Price(12.0));
       	Product productAfter =
    			new Product("1", 45, new Price(12.0));
       	Map<String, Integer> reserved = new HashMap<String, Integer>();
       	reserved.put("cc", 15);
      	Map<String, Integer> reservedAfter = new HashMap<String, Integer>();
       	reservedAfter.put("cc", 5);
      	fixture.given(new ProductCellCreated(productBefore, 50),
      			new ProductGivenToCustomer(product, "cc", reserved, productGiven))
    			.when(new ReturnProductFromCustomer(productReturn, "cc"))
    			.expectEvents(new ProductReturnedFromCustomer(
    					productAfter, "cc", reservedAfter));
    }
    
    @Test
    public void testReturnProductFromCustomerNoExistsCustomerIdException() 
    		throws Exception {
    	Product productBefore =
    			new Product("1", 50, new Price(12.0));
    	Product product =
    			new Product("1", 35, new Price(12.0));
    	Product productGiven =
    			new Product("1", 15, new Price(12.0));
    	Product productReturn =
    			new Product("1", 10, new Price(12.0));
       	Map<String, Integer> reserved = new HashMap<String, Integer>();
       	reserved.put("cc", 15);
    	fixture.given(new ProductCellCreated(productBefore, 50),
    			new ProductGivenToCustomer(product, "cc", reserved, productGiven))
    			.when(new ReturnProductFromCustomer(productReturn, "nnExists"))
    			.expectException(DomainStateException.class);
    }
    
    @Test
    public void testReturnProductFromCustomerTryReturnMoreThanReservedException() 
    		throws Exception {
    	Product productBefore =
    			new Product("1", 50, new Price(12.0));
    	Product product =
    			new Product("1", 35, new Price(12.0));
    	Product productGiven =
    			new Product("1", 15, new Price(12.0));
    	Product productReturn =
    			new Product("1", 100, new Price(12.0));
       	Map<String, Integer> reserved = new HashMap<String, Integer>();
       	reserved.put("cc", 15);
    	fixture.given(new ProductCellCreated(productBefore, 50),
    			new ProductGivenToCustomer(product, "cc", reserved, productGiven))
    			.when(new ReturnProductFromCustomer(productReturn, "cc"))
    			.expectException(DomainStateException.class);
    }
    
    @Test
    public void testReturnProductFromCustomerNoExitsInShoppingCartProductException() 
    		throws Exception {
    	Product productBefore =
    			new Product("1", 50, new Price(12.0));
    	Product productReturn =
    			new Product("1", 10, new Price(12.0));
    	fixture.given(new ProductCellCreated(productBefore, 50))
    			.when(new ReturnProductFromCustomer(productReturn, "cc"))
    			.expectException(DomainStateException.class);
    }
}
