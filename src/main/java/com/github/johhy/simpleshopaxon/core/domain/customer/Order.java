package com.github.johhy.simpleshopaxon.core.domain.customer;

import java.util.HashMap;
import java.util.Map;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import com.github.johhy.simpleshopaxon.core.commands.customer.AddProductToOrderCommand;
import com.github.johhy.simpleshopaxon.core.commands.customer.RemoveProductFromOrderCommand;
import com.github.johhy.simpleshopaxon.core.events.customer.ProductAddedToOrderEvent;
import com.github.johhy.simpleshopaxon.core.events.customer.ProductRemovedFromOrderEvent;
import com.github.johhy.simpleshopaxon.core.exceptions.customer.AmountProductToRemoveNotEqualInOrderException;
import com.github.johhy.simpleshopaxon.core.exceptions.customer.ProductExistsException;
import com.github.johhy.simpleshopaxon.core.exceptions.customer.ProductNotFoundException;
import com.jcabi.aspects.Loggable;

/**
 * Domain object. Not Aggregate Root.
 * <p>
 * Order keeps products.
 * Product can removed or added to order, but when add to order
 * it - old product removed and new product added.
 * <p>
 * Returned events for debug only.
 * 
 * @author johhy
 */
public class Order extends AbstractAnnotatedEntity {
	
	/** The logica of order. */
	private OrderLogica logica;
		
	Map<String, Product> products = new HashMap<String, Product>();
	/**
	 * Instantiates a new order.
	 *
	 * @param logica the logica
	 */
	public Order(OrderLogica logica) {
		this.logica =logica;
	}
	
	
	/**
	 * Adds the product to order command.
	 *
	 * @param command the command
	 * @return the product added to order event
	 * @throws ProductExistsException the product exists exception
	 */
	@Loggable(Loggable.DEBUG)
	@CommandHandler
	public ProductAddedToOrderEvent addProductToOrderCommand(AddProductToOrderCommand command) 
			throws ProductExistsException {
		if(!products.containsKey(command.getCodeOfProduct())) {
		ProductAddedToOrderEvent event = new ProductAddedToOrderEvent(
				command.getCustomerId(),
				command.getOrderId(),
				command.getCodeOfProduct(),
				command.getQuantity(),
				command.getPrice());
		apply(event);
		return event;
		} else 
			throw new ProductExistsException("Product:" + command.getCodeOfProduct() +
				" for customer:" + command.getCustomerId() + " in order:" +
				command.getOrderId() + " exists.You need remove product and than add new");
	}

	/**
	 * Product added event.
	 *
	 * @param event the event
	 */
	@EventSourcingHandler
	public void productAddedEvent(ProductAddedToOrderEvent event) {
		products.put(event.getCodeOfProduct(), 
				new Product(event.getQuantity(), event.getPrice()));
	}

	/**
	 * Removes the product from order command.
	 *
	 * @param command the command
	 * @return the product removed from order event
	 * @throws AmountProductToRemoveNotEqualInOrderException the amount product to remove not equal in order exception
	 * @throws ProductNotFoundException the product not found exception
	 */
	@Loggable(Loggable.DEBUG)
	@CommandHandler
	public ProductRemovedFromOrderEvent removeProductFromOrderCommand(RemoveProductFromOrderCommand command) 
				throws AmountProductToRemoveNotEqualInOrderException, ProductNotFoundException {
		if(products.containsKey(command.getCodeOfProduct())) {
			if(isAmountProductToRemoveEqualInOrder(command)) {
				ProductRemovedFromOrderEvent event = 
					new ProductRemovedFromOrderEvent(
				command.getCustomerId(),
				command.getOrderId(),
				command.getCodeOfProduct());
				apply(event);
				return event;
			} else {
				throw new AmountProductToRemoveNotEqualInOrderException("Amount "
				+ command.getAmountToRemove() + "to remove no equals for product:" 
				+ command.getCodeOfProduct() +
				" for customer:" + command.getCustomerId() + " in order:" + 
				command.getOrderId());
			}
		} else throw new ProductNotFoundException("Product:" + command.getCodeOfProduct() +
			" for customer:" + command.getCustomerId() + " in order:" +
			command.getOrderId() + " not found");
		
	}
	
	/**
	 * Checks if is order empty.
	 *
	 * @return true, if is order empty
	 */
	public boolean isOrderEmpty() {
		return products.isEmpty();
	}
	
	private boolean isAmountProductToRemoveEqualInOrder(RemoveProductFromOrderCommand command) {
		return command.getAmountToRemove()==
				products.get(command.getCodeOfProduct()).getQuantity();
	}

	/**
	 * Product removed event.
	 *
	 * @param event the event
	 */
	@EventSourcingHandler
	public void productRemovedEvent(ProductRemovedFromOrderEvent event) {
		products.remove(event.getCodeOfProduct());
	}

	
}
