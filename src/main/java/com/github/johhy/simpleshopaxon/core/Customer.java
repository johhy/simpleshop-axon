package com.github.johhy.simpleshopaxon.core;

import java.util.Date;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.commandhandling.annotation.CommandHandlingMember;
import org.axonframework.eventsourcing.annotation
						.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcedMember;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import com.github.johhy.simpleshopaxon.core.api.commands.CreateCustomer;
import com.github.johhy.simpleshopaxon.core.api.events.CustomerCreated;
import com.github.johhy.simpleshopaxon.core.api.exception.DomainStateException;
import com.github.johhy.simpleshopaxon.core.api.shared.Product;
import com.jcabi.log.Logger;

/**
* The Class Customer.
* <p>
* Domain object.Aggregate Root.
* Domain command and event handler.
* Keeps customer state and produce logic with customer.
* Returned values in methods use for debug only,
* apply used for save and publish.
* 
* @author johhy
*/
public final class Customer extends AbstractAnnotatedAggregateRoot<String> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The customer id. */
	@AggregateIdentifier
	private String customerId;
	
	/** The created. */
	@SuppressWarnings("unused")
	private Date created;
	
	
	/** The customer info. */
	@CommandHandlingMember
	@EventSourcedMember
	private CustomerInfo customerInfo;
	
	/** The shopping cart. */
	@CommandHandlingMember
	@EventSourcedMember
	private ShoppingCart shoppingCart = new ShoppingCart();
	
	/**
	 * Gets the shopping cart.
	 *
	 * @return the shopping cart
	 */
	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	/**
	 * Instantiates a new customer.
	 */
	public Customer() { }
	
	/**
	 * Instantiates a new customer.
	 *
	 * @param command the command
	 */
	@CommandHandler
	public Customer(final CreateCustomer command) {
		Logger.debug(this, "Customer constructor:" + command);
		apply(new CustomerCreated(command.getCustomerId(), 
				command.getAddress(), command.getCreated()));
	}
	
	/**
	 * Customer created.
	 *
	 * @param event the event
	 */
	@EventSourcingHandler
	private void customerCreated(final CustomerCreated event) {
		customerId = event.getCustomerId();
		created = event.getCreated();
		customerInfo = new CustomerInfo(event.getAddress());
	}
	
	
	/**
	 * Adds the product for customer.
	 *
	 * @param productToAdd the product to add
	 */
	public void addProductForCustomer(final Product productToAdd) {
		shoppingCart.addProductToShoppingCart(productToAdd);
	}
	
	/**
	 * Removes the product from customer.
	 *
	 * @param productToRemove the product to remove
	 * @throws DomainStateException the domain state exception
	 */
	public void removeProductFromCustomer(final Product productToRemove) 
			throws DomainStateException {
		shoppingCart.removeProductFromShoppingCart(productToRemove);
	}


}
