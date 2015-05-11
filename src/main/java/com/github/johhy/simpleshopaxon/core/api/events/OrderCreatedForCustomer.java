package com.github.johhy.simpleshopaxon.core.api.events;

import java.util.Date;
import java.util.List;

import com.github.johhy.simpleshopaxon.core.api.shared.Address;
import com.github.johhy.simpleshopaxon.core.api.shared.Price;
import com.github.johhy.simpleshopaxon.core.api.shared.Product;

/**
 * The Class OrderCreatedForCustomer.
 * 
 * @author johhy
 */
public class OrderCreatedForCustomer {

	/** The customer id. */
	private final String customerId;
	
	/** The order id. */
	private final String orderId;
	
	/** The created. */
	private final Date created;
	
	/** The ship to. */
	private final Address shipTo;
	
	/** The products. */
	private final List<Product> products;
	
	/** The total. */
	private final Price total;

	/**
	 * Instantiates a new order created for customer.
	 *
	 * @param customerIdOwnOrder the customer id own order
	 * @param orderIdCreatedOrder the order id created order
	 * @param orderCreatedDate the order created date
	 * @param addressToShipOrder the address to ship order
	 * @param productsInOrder the products in order
	 * @param totalPriceOrder the total price order
	 */
	public OrderCreatedForCustomer(final String customerIdOwnOrder, 
		final String orderIdCreatedOrder, 
		final Date orderCreatedDate, 
		final Address addressToShipOrder,
		final List<Product> productsInOrder, 
		final Price totalPriceOrder) {
		super();
		this.customerId = customerIdOwnOrder;
		this.orderId = orderIdCreatedOrder;
		this.created = orderCreatedDate;
		this.shipTo = addressToShipOrder;
		this.products = productsInOrder;
		this.total = totalPriceOrder;
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
		return "OrderCreatedForCustomer [customerId=" + customerId 
				+ ", orderId=" + orderId 
				+ ", created=" + created
				+ ", shipTo=" + shipTo 
				+ ", products=" + products 
				+ ", total=" + total + "]";
	}

}
