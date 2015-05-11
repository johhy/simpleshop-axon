package com.github.johhy.simpleshopaxon.web.rest.dto;

import java.util.Date;

/**
 * The Class Order.
 * <p>
 * 
 * @author johhy
 */
public final class Order {

	/** The order id. */
	private final String orderId;
	
	/** The customer id. */
	private final String customerId;
	
	/** The ship to. */
	private final String shipTo;
	
	/** The order satus. */
	private final String orderStatus;
	
	/** The total. */
	private final Double total;
	
	/** The created. */
	private final Date created;
	
	/** The product id. */
	private final String productId;

	/** The quantity. */
	private final Integer quantity;
	
	/** The price. */
	private final Double price;

	/**
	 * Instantiates a new order.
	 *
	 * @param newOrderId the new order id
	 * @param newCustomerId the new customer id
	 * @param addressToShip the address to ship
	 * @param newOrderStatus the new order status
	 * @param newTotal the new total
	 * @param newCreated the new created
	 * @param newProductId the new product id
	 * @param newQuantity the new quantity
	 * @param newPrice the new price
	 */
	public Order(final String newOrderId, 
		final String newCustomerId, 
		final String addressToShip, final String newOrderStatus, 
		final Double newTotal, final Date newCreated,
		final String newProductId, 
		final Integer newQuantity, final Double newPrice) {
		super();
		this.orderId = newOrderId;
		this.customerId = newCustomerId;
		this.shipTo = addressToShip;
		this.orderStatus = newOrderStatus;
		this.total = newTotal;
		this.created = newCreated;
		this.productId = newProductId;
		this.quantity = newQuantity;
		this.price = newPrice;
	}

	/**
	 * Gets the order id.
	 *
	 * @return the order id
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * Gets the customer id.
	 *
	 * @return the customer id
	 */
	public String getCustomerId() {
		return customerId;
	}

	/**
	 * Gets the ship to.
	 *
	 * @return the ship to
	 */
	public String getShipTo() {
		return shipTo;
	}

	/**
	 * Gets the order satus.
	 *
	 * @return the order satus
	 */
	public String getOrderSatus() {
		return orderStatus;
	}

	/**
	 * Gets the total.
	 *
	 * @return the total
	 */
	public Double getTotal() {
		return total;
	}

	/**
	 * Gets the created.
	 *
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * Gets the product id.
	 *
	 * @return the product id
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Order [orderId=" + orderId 
			+ ", customerId=" + customerId 
			+ ", shipTo=" + shipTo 
			+ ", orderSatus=" + orderStatus 
			+ ", total=" + total 
			+ ", created=" + created 
			+ ", productId=" + productId 
			+ ", quantity=" + quantity 
			+ ", price=" + price + "]";
	}


}
