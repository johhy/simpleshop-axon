package com.github.johhy.simpleshopaxon.core.api.commands;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.github.johhy.simpleshopaxon.core.api.shared.Address;
import com.github.johhy.simpleshopaxon.core.api.shared.Price;
import com.github.johhy.simpleshopaxon.core.api.shared.Product;

/**
 * The Class CreateOrder.
 * 
 * @author johhy
 */
public class CreateOrder {

/** The customer id. */
private final String customerId;
	
	/** The order id. */
	@NotBlank
	private final String orderId;
	
	/** The created. */
	@NotNull
	private final Date created;
	
	/** The ship to. */
	@NotNull
	@Valid
	private final Address shipTo;
	
	/** The products. */
	@NotEmpty
	private final List<Product> products;
	
	/** The total. */
	@NotNull
	@Valid
	private final Price total;

	/**
	 * Instantiates a new creates the order.
	 *
	 * @param customerIdOwnOrder the customer id own order
	 * @param orderIdCreated the order id created
	 * @param orderCreatedDate the order created date
	 * @param addressToShip the address to ship
	 * @param productsInOrder the products in order
	 * @param totalPriceOfOrder the total price of order
	 */
	public CreateOrder(final String customerIdOwnOrder, 
		final String orderIdCreated, final Date orderCreatedDate, 
		final Address addressToShip, final List<Product> productsInOrder,
		final Price totalPriceOfOrder) {
		super();
		this.customerId = customerIdOwnOrder;
		this.orderId = orderIdCreated;
		this.created = orderCreatedDate;
		this.shipTo = addressToShip;
		this.products = productsInOrder;
		this.total = totalPriceOfOrder;
	}

	/**
	 * Gets the customer id.
	 *
	 * @return the customer id
	 */
	public final String getCustomerId() {
		return customerId;
	}

	/**
	 * Gets the order id.
	 *
	 * @return the order id
	 */
	public final String getOrderId() {
		return orderId;
	}

	/**
	 * Gets the created.
	 *
	 * @return the created
	 */
	public final Date getCreated() {
		return created;
	}

	/**
	 * Gets the ship to.
	 *
	 * @return the ship to
	 */
	public final Address getShipTo() {
		return shipTo;
	}

	/**
	 * Gets the products.
	 *
	 * @return the products
	 */
	public final List<Product> getProducts() {
		return products;
	}

	/**
	 * Gets the total.
	 *
	 * @return the total
	 */
	public final Price getTotal() {
		return total;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		return "CreateOrder [customerId=" + customerId 
			+ ", orderId=" + orderId 
			+ ", created=" + created 
			+ ", shipTo=" + shipTo 
			+ ", products=" + products 
			+ ", total=" + total + "]";
	}

}
