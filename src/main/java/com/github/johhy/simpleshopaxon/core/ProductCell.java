package com.github.johhy.simpleshopaxon.core;

import java.util.HashMap;
import java.util.Map;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation
						.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;
import org.axonframework.repository.AggregateNotFoundException;
import org.axonframework.repository.Repository;

import com.github.johhy.simpleshopaxon.core.api.commands
						.AddProductToProductCell;
import com.github.johhy.simpleshopaxon.core.api.commands
						.ChangeCapacityProductCell;
import com.github.johhy.simpleshopaxon.core.api.commands
						.ChangePriceInProductCell;
import com.github.johhy.simpleshopaxon.core.api.commands.CreateProductCell;
import com.github.johhy.simpleshopaxon.core.api.commands.GiveProductToCustomer;
import com.github.johhy.simpleshopaxon.core.api.commands
						.RemoveProductFromProductCell;
import com.github.johhy.simpleshopaxon.core.api.commands
						.ReturnProductFromCustomer;
import com.github.johhy.simpleshopaxon.core.api.events
						.CapacityProductCellChanged;
import com.github.johhy.simpleshopaxon.core.api.events
						.ProductAddedToProductCell;
import com.github.johhy.simpleshopaxon.core.api.events.ProductCellCreated;
import com.github.johhy.simpleshopaxon.core.api.events.ProductGivenToCustomer;
import com.github.johhy.simpleshopaxon.core.api.events
						.AbstractProductInProductCellChanged;
import com.github.johhy.simpleshopaxon.core.api.events
						.ProductPriceInProductCellChanged;
import com.github.johhy.simpleshopaxon.core.api.events
						.ProductRemovedFromProductCell;
import com.github.johhy.simpleshopaxon.core.api.events
						.ProductReturnedFromCustomer;
import com.github.johhy.simpleshopaxon.core.api.events
						.AbstractReservedProductChanged;
import com.github.johhy.simpleshopaxon.core.api.exception.DomainStateException;
import com.github.johhy.simpleshopaxon.core.api.shared.Price;
import com.github.johhy.simpleshopaxon.core.api.shared.Product;
import com.jcabi.aspects.Loggable;
import com.jcabi.log.Logger;

/**
 * The Class ProductCell.
 * <p>
 * Domain object.Aggregate Root.
 * Domain command and event handler.
 * Keeps product state and produce logic with product.
 * Returned values in methods use for debug only,
 * apply used for save and publish.
 * 
 * @author johhy
 */
public final class ProductCell extends AbstractAnnotatedAggregateRoot<String> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The product id. */
	@AggregateIdentifier
	private String productId;
	
	/** The product. */
	private Product product;

	/** The capacity. */
	private Integer capacity;
	
	/** The reserved. */
	private Map<String, Integer> reserved;
	
	/**
	 * Instantiates a new product cell.
	 */
	public ProductCell() { };
	
	/**
	 * Instantiates a new product cell.
	 *
	 * @param command the command
	 */
	@CommandHandler
	public ProductCell(final CreateProductCell command) {
		Logger.debug(this, "ProductCell constructor:" + command);
		apply(new ProductCellCreated(command.getProduct(), 
				command.getProduct().getQuantity()));
	}
	
	/**
	 * Product cell created.
	 *
	 * @param event the event
	 */
	@EventSourcingHandler
	private void productCellCreated(final ProductCellCreated event) {
		productId = event.getProductId();
		product = event.getProduct();
		capacity = event.getCapacity();
		reserved = new HashMap<String, Integer>();
	}
	
	/**
	 * Adds the amount product.
	 *
	 * @param command the command
	 * @throws DomainStateException the domain state exception
	 */
	@CommandHandler
	@Loggable(trim = false, prepend = true, value = Loggable.DEBUG)
	private void addAmountProduct(final AddProductToProductCell command) 
			throws DomainStateException {
		addAmountProduct(command.getProduct());
		publish(new ProductAddedToProductCell(product));
	}
	
	/**
	 * Removes the amount product.
	 *
	 * @param command the command
	 * @throws DomainStateException the domain state exception
	 */
	@CommandHandler
	@Loggable(trim = false, prepend = true, value = Loggable.DEBUG)
	private void removeAmountProduct(
		final RemoveProductFromProductCell command) 
			throws DomainStateException {
		removeAmountProduct(command.getProduct());
		publish(new ProductRemovedFromProductCell(product));
	}
	
	/**
	 * Change price.
	 *
	 * @param command the command
	 */
	@CommandHandler
	@Loggable(trim = false, prepend = true, value = Loggable.DEBUG)
	private void changePrice(final ChangePriceInProductCell command) {
		product = new Product(productId, product.getQuantity(), 
				new Price(command.getProduct().getPrice().getValue()));
		publish(new ProductPriceInProductCellChanged(product));
	}
	
	/**
	 * Product changed.
	 *
	 * @param event the event
	 */
	@EventSourcingHandler
	private void 
	productChanged(final AbstractProductInProductCellChanged event) {
		product = event.getProduct();
	}
	
	/**
	 * Change capacity.
	 *
	 * @param command the command
	 */
	@CommandHandler
	@Loggable(trim = false, prepend = true, value = Loggable.DEBUG)
	private void changeCapacity(final ChangeCapacityProductCell command) {
		if (product.getQuantity() <= command.getCapacity()) {
			publish(new CapacityProductCellChanged(product, 
					command.getCapacity()));
		} else {
		    throw new DomainStateException("Product in cell:" 
			    + product.getQuantity() + " is more than setup " 
			    + "capacity:" + command.getCapacity());
		}
	}
	
	/**
	 * Capacity changed.
	 *
	 * @param event the event
	 */
	@EventSourcingHandler
	private void capacityChanged(final CapacityProductCellChanged event) {
		capacity = event.getCapacity();
	}
	
	/**
	 * Give product to customer.
	 *
	 * @param command the command
	 * @param cRepo the c repo
	 */
	@CommandHandler
	@Loggable(trim = false, prepend = true, value = Loggable.DEBUG)
	private void giveProductToCustomer(final GiveProductToCustomer command,
			final Repository<Customer> cRepo) {
			removeAmountProduct(command.getProduct());
			Integer amountReserved = reserved.get(command.getCustomerId());
			if (amountReserved == null) {
				reserved.put(command.getCustomerId(), 
						command.getProduct().getQuantity());
			} else {
				reserved.put(command.getCustomerId(),
						amountReserved + command.getProduct().getQuantity());
			}
			Product givenProduct = new Product(productId, 
					command.getProduct().getQuantity(), 
					product.getPrice());
			publish(new ProductGivenToCustomer(product, command.getCustomerId(),
						reserved, givenProduct));
			Customer customer = null;
			try {
				customer = cRepo.load(command.getCustomerId());
			} catch (AggregateNotFoundException e) {
				throw new DomainStateException("Customer:" 
						+ command.getCustomerId() + "no found");
			}
			customer.addProductForCustomer(givenProduct);
	}
	
	/**
	 * Return product from customer.
	 *
	 * @param command the command
	 * @param cRepo the c repo
	 */
	@CommandHandler
	@Loggable(trim = false, prepend = true, value = Loggable.DEBUG)
	private void returnProductFromCustomer(
		final ReturnProductFromCustomer command,
			final Repository<Customer> cRepo) {
			addAmountProduct(command.getProduct());
			Integer amountReserved = reserved.get(command.getCustomerId());
			Integer amountToRemove = command.getProduct().getQuantity();
			if (amountReserved != null) {
				if (amountReserved >= amountToRemove) {
					reserved.remove(command.getCustomerId());
					Integer amountAfter = amountReserved - amountToRemove;
					if (amountAfter > 0) {
					    reserved.put(command.getCustomerId(), amountAfter);
					}
					publish(new ProductReturnedFromCustomer(
						product, command.getCustomerId(), reserved));
				} else {
				    throw new DomainStateException("Reserved product in cell:" 
				    			+ amountReserved + " for customer:" 
				    			+ command.getCustomerId() 
				    			+ " less than need to remove:" 
				    			+ amountReserved);
				}
			} else {
			    throw new DomainStateException("Reserved product no found for:" 
				    		+ command.getCustomerId());
			}
			
			Customer customer = null;
			try {
				customer = cRepo.load(command.getCustomerId());
			} catch (AggregateNotFoundException e) {
				throw new DomainStateException("Customer:" 
						+ command.getCustomerId() + "no found");
			}
			customer.addProductForCustomer(command.getProduct());
	}
	
	/**
	 * Reserved product changed.
	 *
	 * @param event the event
	 */
	@EventSourcingHandler
	private void 
	reservedProductChanged(final AbstractReservedProductChanged event) {
		product = event.getProduct();
		reserved = event.getReserved();
	}
	
	/**
	 * Removes the amount product.
	 *
	 * @param productToRemove the product to remove
	 * @return the product
	 */
	@Loggable(trim = false, prepend = true, value = Loggable.DEBUG)
	private Product removeAmountProduct(final Product productToRemove) {
		product = product.removeAmount(productToRemove);
		if (product.getQuantity() >= 0) {
			return product;
		} else {
		    throw new DomainStateException("Quantity product:" 
			    + product.getProductId() + " less than need to remove:"
			    + productToRemove.getQuantity());
		}
	}
	
	/**
	 * Adds the amount product.
	 *
	 * @param productToAdd the product to add
	 * @return the product
	 */
	@Loggable(trim = false, prepend = true, value = Loggable.DEBUG)
	private Product addAmountProduct(final Product productToAdd) {
		product = product.addAmount(productToAdd);
		if (product.getQuantity() <= capacity) {
			return product;
		} else {
		    throw new DomainStateException("Capacity product:" 
			    	+ product.getProductId() + " less than need to add:" 
			    	+ productToAdd.getQuantity());
		}
	}
	
	/**
	 * Publish.
	 *
	 * @param event the event
	 */
	@Loggable(trim = false, prepend = false, value = Loggable.DEBUG)
	private void publish(final Object event) {
		apply(event);
	}

}
