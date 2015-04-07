package com.github.johhy.simpleshopaxon.core.domain.productcell;


import java.util.ArrayList;
import java.util.List;

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

	/** The logica of product cell. */
	@Transient
	private ProductCellLogica logica;
	
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
	 * @param logica the logica
	 */
	public ProductCell(ProductCellLogica logica) {
		this.logica = logica;
	}
	
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
	 * @return the product quantity changed in product cell event
	 */
	@Loggable(Loggable.DEBUG)
	@CommandHandler
	public ProductQuantityChangedInProductCellEvent 
		addAmountProductToProductCellCommand(
			AddProductToProductCellCommand command) {
		ProductQuantityChangedInProductCellEvent event =
				new ProductQuantityChangedInProductCellEvent(command.getCodeOfProduct(),
						logica.addAmountProductToProductCell(command.getQuantity()));
		apply(event);
		return event;
	}
	
	/**
	 * Removes the amount product from product cell command.
	 *
	 * @param command the command
	 * @return the product quantity changed in product cell event
	 * @throws RemoveProductLessThanNeedException the remove product less than need exception
	 */
	@Loggable(Loggable.DEBUG)
	@CommandHandler
	public ProductQuantityChangedInProductCellEvent 
		removeAmountProductFromProductCellCommand(
			RemoveProductFromProductCellCommand command)
			throws RemoveProductLessThanNeedException {
		if(logica.isProductMoreThanNeed(command.getQuantity())) {
			ProductQuantityChangedInProductCellEvent event = 
					new ProductQuantityChangedInProductCellEvent(command.getCodeOfProduct(),
							logica.removeAmountProductFromProductCell(command.getQuantity()));
			apply(event);
			return event;
		} else throw new RemoveProductLessThanNeedException("Quantity req:" + command.getQuantity() +
				" in product cell is:" + logica.getQuantity());
	}
	
	/**
	 * Product quantity changed in product cell event.
	 *
	 * @param event the event
	 */
	@EventSourcingHandler
	public void productQuantityChangedInProductCellEvent(
			ProductQuantityChangedInProductCellEvent event) {
		logica.setQuantity(event.getQuantity());
	}
	
	/**
	 * Change price in product cell command.
	 *
	 * @param command the command
	 * @return the price product changed event
	 */
	@Loggable(Loggable.DEBUG)
	@CommandHandler
	public PriceProductChangedEvent 
		changePriceInProductCellCommand(ChangePriceOfProductCommand command) {
		PriceProductChangedEvent event = 
				new PriceProductChangedEvent(command.getCodeOfProduct(), 
						logica.changePrice(command.getPrice()));
		apply(event);
		return event;
	}
	
	/**
	 * Product price changed in product cell event.
	 *
	 * @param event the event
	 */
	@EventSourcingHandler
	public void productPriceChangedInProductCellEvent(PriceProductChangedEvent event) {
		logica.setPrice(event.getPrice());
	}
	
	/**
	 * Give amount product from product cell for order command.
	 *
	 * @param command the command
	 * @return the list
	 * @throws ProductInProductCellLessThanNeedException the product in product cell less than need exception
	 */
	@Loggable(Loggable.DEBUG)
	@CommandHandler
	public List<Object> giveAmountProductFromProductCellForOrderCommand(
			GiveAmountProductFromProductCellForOrderCommand command)
			throws ProductInProductCellLessThanNeedException {
		if(logica.isProductMoreThanNeed(command.getAmount())) {
			//remove from quantity
			ProductQuantityChangedInProductCellEvent event1 = 
					new ProductQuantityChangedInProductCellEvent(command.getCodeOfProduct(),
							logica.removeAmountProductFromProductCell(command.getAmount()));
			//add to reserved
			ReservedProductChangedInProductCellEvent event2 =
					new ReservedProductChangedInProductCellEvent(command.getCodeOfProduct(),
							command.getOrderId(), 
								logica.addReservedProductForOrder(command.getOrderId(), 
										command.getAmount()));
			apply(event1);
			apply(event2);
			List<Object> eventsList = new ArrayList<Object>();
			eventsList.add(event1);
			eventsList.add(event2);
			return eventsList;
		} else throw new ProductInProductCellLessThanNeedException("");
	}
	
	/**
	 * Return amount product from order to product cell command.
	 *
	 * @param command the command
	 * @return the list
	 * @throws OrderNotFoundException the order not found exception
	 * @throws ReservedProductInProductCellLessThanReturnedFromOrderException the reserved product in product cell less than returned from order exception
	 */
	@Loggable(Loggable.DEBUG)
	@CommandHandler
	public List<Object> returnAmountProductFromOrderToProductCellCommand(
			ReturnAmountProductFromOrderToProductCellCommand command)
			throws OrderNotFoundException,
			ReservedProductInProductCellLessThanReturnedFromOrderException {
		if(logica.isProductReservedForOrder(command.getOrderId())) {
			if(logica.isAmountReservedProductMoreThanNeedToRemove(command.getOrderId(), 
					command.getAmount())) {
				//add to quantity
				ProductQuantityChangedInProductCellEvent event1 =
						new ProductQuantityChangedInProductCellEvent(command.getCodeOfProduct(),
								logica.addAmountProductToProductCell(command.getAmount()));
				//remove from reserved
				ReservedProductChangedInProductCellEvent event2 =
						new ReservedProductChangedInProductCellEvent(command.getCodeOfProduct(),
								command.getOrderId(),
								logica.removeAmountReservedProductForOrder(command.getOrderId(), 
										command.getAmount()));
				apply(event1);
				apply(event2);
				List<Object> eventsList = new ArrayList<Object>();
				eventsList.add(event1);
				eventsList.add(event2);
				return eventsList;
			} else throw new ReservedProductInProductCellLessThanReturnedFromOrderException("");
		} else throw new OrderNotFoundException("");
		
	}
	
	/**
	 * Reserved product changed in product cell event.
	 *
	 * @param event the event
	 */
	@EventSourcingHandler
	public void reservedProductChangedInProductCellEvent(
			ReservedProductChangedInProductCellEvent event) {
		logica.reserveProductForOrder(event.getOrderId(), event.getAmount());
	}

	/**
	 * Removes the product cell.
	 *
	 * @param command the command
	 * @return the product cell removed event
	 * @throws ReservedProductsExistsInProductCellException the reserved products exists in product cell exception
	 */
	@Loggable(Loggable.DEBUG)
	@CommandHandler
	public ProductCellRemovedEvent removeProductCell(RemoveProductCellCommand command)
			throws ReservedProductsExistsInProductCellException {
		if(!logica.isAnyReservedProductInProductCell()) {
			ProductCellRemovedEvent event = 
					new ProductCellRemovedEvent(command.getCodeOfProduct());
			apply(event);
			return event;
		} else throw new ReservedProductsExistsInProductCellException("");
		
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
