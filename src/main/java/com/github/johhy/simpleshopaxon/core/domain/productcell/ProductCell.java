package com.github.johhy.simpleshopaxon.core.domain.productcell;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import com.github.johhy.simpleshopaxon.core.commands.productcell.AddProductToProductCellCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.ChangePriceOfProductCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.CreateProductCellCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.GiveAmountProductFromProductCellForOrderCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.RemoveProductCellCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.RemoveProductFromProductCellCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.ReturnAmountProductFromOrderToProductCellCommand;
import com.github.johhy.simpleshopaxon.core.events.productcell.PriceProductChangedEvent;
import com.github.johhy.simpleshopaxon.core.events.productcell.ProductCellCreatedEvent;
import com.github.johhy.simpleshopaxon.core.events.productcell.ProductCellRemovedEvent;
import com.github.johhy.simpleshopaxon.core.events.productcell.ProductQuantityChangedInProductCellEvent;
import com.github.johhy.simpleshopaxon.core.events.productcell.ReservedProductChangedInProductCellEvent;
import com.github.johhy.simpleshopaxon.core.exceptions.customer.OrderNotFoundException;
import com.github.johhy.simpleshopaxon.core.exceptions.productcell.ReservedProductInProductCellLessThanReturnedFromOrderException;
import com.github.johhy.simpleshopaxon.core.exceptions.productcell.ProductInProductCellLessThanNeedException;
import com.github.johhy.simpleshopaxon.core.exceptions.productcell.RemoveProductLessThanNeedException;
import com.github.johhy.simpleshopaxon.core.exceptions.productcell.ReservedProductsExistsInProductCellException;
import com.jcabi.aspects.Loggable;

/**
 * The Class ProductCell.
 *
 * @author johhy
 * <p>
 * Domain object. Aggregate Root.
 * <p>
 * Keep product values that can be changed.If client ask for product
 * it reduce quantity and add amount asked product as reserved product.
 * Each reserved product identify by order and unique.When product returned 
 * quantity increase equal returned amount and equal amount reserved 
 * product removed too.All changed event publish.
 * Rules is:
 * 1.For one product is one product cell identify by code of product.
 * 2.If product reserved for any clients no can be deleted.
 * 3.Amount reserved product only for one order.
 * <p>
 * Returned events for debug only.
 */
public class ProductCell extends AbstractAnnotatedAggregateRoot<String> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The quantity. */
	private int quantity = 0;
	
	/** The price. */
	private double price = 0;
	
	/** The reserved products. 
	 * Key is code of product, value is quantity*/
	Map<String, Integer> reservedProducts =	new HashMap<String, Integer>();
	

	/** The code of product. */
	@AggregateIdentifier
	private String codeOfProduct;
	
	/**
	 * Instantiates a new product cell.
	 */
	public ProductCell() {};
	

	/**
	 * Instantiates a new product cell.
	 *
	 * @param command the command
	 */
	@CommandHandler
	public ProductCell(CreateProductCellCommand command) {
		apply(new ProductCellCreatedEvent(command.getCodeOfProduct()));
	}
	
	/**
	 * Product cell created.
	 *
	 * @param event the event
	 */
	@EventSourcingHandler
	public void productCellCreated(ProductCellCreatedEvent event) {
		this.codeOfProduct = event.getCodeOfProduct();
	}
	
	/**
	 * Adds the amount product to product cell command.
	 *
	 * @param command the command
	 * @return the event for debug
	 */
	@Loggable(Loggable.DEBUG)
	@CommandHandler
	public Object addAmountProductToProductCellCommand(
			AddProductToProductCellCommand command) {
		return applyAndLog(new ProductQuantityChangedInProductCellEvent(
				command.getCodeOfProduct(),
				quantity + command.getQuantity()));
	}
	
	/**
	 * Removes the amount product from product cell command.
	 *
	 * @param command the command
	 * @return the event for debug
	 */
	@Loggable(Loggable.DEBUG)
	@CommandHandler
	public Object removeAmountProductFromProductCellCommand(
			RemoveProductFromProductCellCommand command) {
			return applyAndLog(
					new ProductQuantityChangedInProductCellEvent(
							command.getCodeOfProduct(),
							quantity - command.getQuantity()));
	}
	
	/**
	 * Product quantity changed in product cell event.
	 *
	 * @param event the event
	 */
	@EventSourcingHandler
	public void productQuantityChangedInProductCellEvent(
			ProductQuantityChangedInProductCellEvent event) {
		quantity = event.getQuantity();
	}
	
	/**
	 * Change price in product cell command.
	 *
	 * @param command the command
	 * @return the event for debug
	 */
	@Loggable(Loggable.DEBUG)
	@CommandHandler
	public Object
		changePriceInProductCellCommand(ChangePriceOfProductCommand command) {
		return applyAndLog(new PriceProductChangedEvent(
				command.getCodeOfProduct(), 
				command.getPrice()));
	}
	
	/**
	 * Product price changed in product cell event.
	 *
	 * @param event the event
	 */
	@EventSourcingHandler
	public void productPriceChangedInProductCellEvent(PriceProductChangedEvent event) {
		price = event.getPrice();
	}
	
	/**
	 * Give amount product from product cell for order command.
	 *
	 * @param command the command
	 * @return the list of events for debug
	 */
	@Loggable(Loggable.DEBUG)
	@CommandHandler
	public List<Object> giveAmountProductFromProductCellForOrderCommand(
			GiveAmountProductFromProductCellForOrderCommand command) {
			return applyAndLogTwo(
					new ProductQuantityChangedInProductCellEvent(
							command.getCodeOfProduct(),
							quantity - command.getAmount()),
					new ReservedProductChangedInProductCellEvent(
							command.getCodeOfProduct(),
							command.getOrderId(), 
							addReservedProductForOrder(
									command.getOrderId(), 
									command.getAmount()))
					);
	}
	
	/**
	 * Return amount product from order to product cell command.
	 *
	 * @param command the command
	 * @return the list of events for debug
	 */
	@Loggable(Loggable.DEBUG)
	@CommandHandler
	public List<Object> returnAmountProductFromOrderToProductCellCommand(
			ReturnAmountProductFromOrderToProductCellCommand command) {
	     return applyAndLogTwo(
	    		 new ProductQuantityChangedInProductCellEvent(
	    				 command.getCodeOfProduct(),
	    				 quantity + command.getAmount()),
	    		 new ReservedProductChangedInProductCellEvent(
	    				 command.getCodeOfProduct(),
						command.getOrderId(),
						removeAmountReservedProductForOrder(
								command.getOrderId(), 
								command.getAmount()))
	    		 );
	}
	
	/**
	 * Reserved product changed in product cell event.
	 *
	 * @param event the event
	 */
	@EventSourcingHandler
	public void reservedProductChangedInProductCellEvent(
			ReservedProductChangedInProductCellEvent event) {
		reservedProducts.put(event.getOrderId(), event.getAmount());
	}

	/**
	 * Removes the product cell.
	 *
	 * @param command the command
	 * @return the event for debug
	 */
	@Loggable(Loggable.DEBUG)
	@CommandHandler
	public Object removeProductCell(RemoveProductCellCommand command) {
			return applyAndLog(new ProductCellRemovedEvent(
					command.getCodeOfProduct()));
	}

	/**
	 * Product cell removed event.
	 *
	 * @param event the event
	 */
	@EventSourcingHandler
	public void productCellRemovedEvent(ProductCellRemovedEvent event) {
		markDeleted();
	}
	
	/**
	 * Adds the reserved product for order.
	 *
	 * @param orderId the order id
	 * @param amountToAdd the amount to add
	 * @return the amount reserved product for order after add
	 */
	@Loggable(Loggable.DEBUG)
	private int addReservedProductForOrder(String orderId, int amountToAdd) {
		int newAmount = 0;
		if(reservedProducts.containsKey(orderId)) {
			newAmount = reservedProducts.get(orderId) + amountToAdd;
		} else {
			newAmount = amountToAdd;
		}
		return newAmount;
	}
	
	/**
	 * Removes the amount reserved product for order.
	 *
	 * @param orderId the order id
	 * @param amountToRemove the amount to remove
	 * @return the amount reserved product for order after dec
	 */
	@Loggable(Loggable.DEBUG)
	private int removeAmountReservedProductForOrder(String orderId, int amountToRemove) {
		return reservedProducts.get(orderId) - amountToRemove;
	}
	
	/**
	 * Reserve product for order.
	 *
	 * @param orderId the order id
	 * @param amountToReserve the amount to reserve
	 */
	@Loggable(Loggable.DEBUG)
	private void reserveProductForOrder(String orderId, int amountToReserve) {
		if(amountToReserve==0) {
			reservedProducts.remove(orderId);
		} else reservedProducts.put(orderId, amountToReserve);
	}
	
	/**
	 * Apply and log.
	 *
	 * @param event the event
	 * @return the event
	 */
	private Object applyAndLog(Object event) {
		apply(event);
		return event;
	}
	
	/**
	 * Apply and log two.
	 *
	 * @param event1 the event1
	 * @param event2 the event2
	 * @return the list of events
	 */
	private List<Object> applyAndLogTwo(Object event1, Object event2) {
		apply(event1);
		apply(event2);
		List<Object> eventsList = new ArrayList<Object>();
		eventsList.add(event1);
		eventsList.add(event2);
		return eventsList;
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeOfProduct == null) ? 0 : codeOfProduct.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ProductCell))
			return false;
		ProductCell other = (ProductCell) obj;
		if (codeOfProduct == null) {
			if (other.codeOfProduct != null)
				return false;
		} else if (!codeOfProduct.equals(other.codeOfProduct))
			return false;
		return true;
	}

}
