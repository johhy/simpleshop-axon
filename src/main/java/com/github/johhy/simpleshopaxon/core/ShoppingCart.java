package com.github.johhy.simpleshopaxon.core;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import com.github.johhy.simpleshopaxon.core.api.commands.ClearShoppingCart;
import com.github.johhy.simpleshopaxon.core.api.commands.CreateOrderForCustomer;
import com.github.johhy.simpleshopaxon.core.api.events.OrderCreatedForCustomer;
import com.github.johhy.simpleshopaxon.core.api.events.ShoppingCartChanged;
import com.github.johhy.simpleshopaxon.core.api.exception.DomainStateException;
import com.github.johhy.simpleshopaxon.core.api.shared.Price;
import com.github.johhy.simpleshopaxon.core.api.shared.Product;
import com.jcabi.aspects.Loggable;


/**
 * The Class ShoppingCart.
 * <p>
 * Domain object.Entity belongs to customer.
 * Domain command and event handler.
 * Keeps shopping cart and produce logic with shopping cart.
 * When customer ask create new order - new order created fills
 * all required fields and calculate total price of order.
 * Than rise event to handle them order creator service.
 * Returned values in methods use for debug only,
 * apply used for save and publish.
 * 
 * @author johhy
 */
public final class ShoppingCart extends AbstractAnnotatedEntity {

	/** The products. */
	private List<Product> products = new ArrayList<Product>();
	
	/**
	 * Adds the product to shopping cart.
	 *
	 * @param productToAdd the product to add
	 * @return the shopping cart changed
	 */
	@Loggable(trim = false, prepend = true, value = Loggable.DEBUG)
	public ShoppingCartChanged addProductToShoppingCart(
		final Product productToAdd) {
		Product foundProduct = findProduct(productToAdd);
		products.remove(foundProduct);
		if (foundProduct == null) {
			products.add(productToAdd);
		} else {
			products.add(foundProduct.addAmount(productToAdd));
		}
		return amountProductChanged(getAggregateRoot().getIdentifier());
	}
	
	/**
	 * Removes the product from shopping cart.
	 *
	 * @param productToRemove the product to remove
	 * @return the shopping cart changed
	 */
	@Loggable(trim = false, prepend = true, value = Loggable.DEBUG)
	public ShoppingCartChanged removeProductFromShoppingCart(
		final Product productToRemove) {
		Product foundProduct = findProduct(productToRemove);
		if (foundProduct != null) {
			Product productAfterRemove = foundProduct
					.removeAmount(productToRemove);
			if (productAfterRemove.getQuantity() >= 0) {
				products.remove(foundProduct);
				if (productAfterRemove.getQuantity() > 0) {
				    products.add(productAfterRemove);
				}
				return amountProductChanged(getAggregateRoot().getIdentifier());
			} else {
			    throw new DomainStateException("In shopping cart amount:" 
				    + foundProduct.getQuantity() + " less than need to remove:" 
				    + productToRemove.getQuantity());
			}
		} else {
		    throw new DomainStateException("Product in shopping cart to remove:"
			    	+ productToRemove.getProductId() + " no found");
		}
	}
	
	/**
	 * Clear shopping cart.
	 *
	 * @param command the command
	 * @return the shopping cart changed
	 */
	@CommandHandler
	@Loggable(trim = false, prepend = false, value = Loggable.DEBUG)
	private ShoppingCartChanged clearShoppingCart(
		final ClearShoppingCart command) {
		products.clear();
		ShoppingCartChanged event =
				new ShoppingCartChanged(command.getCustomerId(), products);
		apply(event);
		return event;
	}
	
	/**
	 * Amount product changed.
	 *
	 * @param object the object
	 * @return the shopping cart changed
	 */
	private ShoppingCartChanged amountProductChanged(final Object object) {
		ShoppingCartChanged event =
				new ShoppingCartChanged(object.toString(), products);
		apply(event);
		return event;
	}
	
	/**
	 * Shopping cart changed.
	 *
	 * @param event the event
	 */
	@EventSourcingHandler
	private void shoppingCartChanged(final ShoppingCartChanged event) {
		products = event.getProducts();
	}
	
	/**
	 * Creates the order for customer.
	 *
	 * @param command the command
	 * @return the order created for customer
	 */
	@CommandHandler
	@Loggable(trim = false, prepend = false, value = Loggable.DEBUG)
	private OrderCreatedForCustomer createOrderForCustomer(
		final CreateOrderForCustomer command) {
		if (!products.isEmpty()) {
			OrderCreatedForCustomer event =
					new OrderCreatedForCustomer(command.getCustomerId(), 
							command.getOrderId(), command.getCreated(), 
							command.getShipTo(), products, 
							new Price(calculateAll()));
			apply(event);
			return event;
		} else {
		    throw new DomainStateException("Shopping cart empty."
			    	+ "Can not create order for:" + command.getCustomerId());
		}
	}
	
	
	/**
	 * Find product.
	 *
	 * @param productToFind the product to find
	 * @return the product
	 */
	@Loggable(trim = false, prepend = false, value = Loggable.DEBUG)
	private Product findProduct(final Product productToFind) {
		for (Product product:products) {
			if (product.getProductId().equals(productToFind.getProductId())) {
			    return product;
			}
		}
		return null;
	}
	
	/**
	 * Calculate all.
	 *
	 * @return the double
	 */
	@Loggable(trim = false, prepend = false, value = Loggable.DEBUG)
	private Double calculateAll() {
		Double total = 0.0;
		for (Product product:products) {
			total = total + (product.getQuantity() 
				*  product.getPrice().getValue());
		}
		return total;
	}
}
