package com.github.johhy.simpleshopaxon.web.rest.dto;

import java.util.Date;

/**
 * The Class Customer.
 * <p>
 * 
 * @author johhy
 */
public final class Customer {

	/** The customer id. */
	private final String customerId;
	
	/** The address. */
	private final String address;
	
	/** The created. */
	private final Date created;

	/** The product id. */
	private final String productId;

	/** The quantity. */
	private final Integer quantity;
	
	/** The price. */
	private final Double price;

	/**
	 * Instantiates a new customer.
	 *
	 * @param newCustomerId the new customer id
	 * @param newAddress the new address
	 * @param newCreated the new created
	 * @param newProductId the new product id
	 * @param newQuantity the new quantity
	 * @param newPrice the new price
	 */
	public Customer(final String newCustomerId, final String newAddress, 
		final Date newCreated, final String newProductId, 
		final Integer newQuantity, final Double newPrice) {
		super();
		this.customerId = newCustomerId;
		this.address = newAddress;
		this.created = newCreated;
		this.productId = newProductId;
		this.quantity = newQuantity;
		this.price = newPrice;
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
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress() {
		return address;
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
		return "Customer [customerId=" + customerId 
			+ ", address=" + address 
			+ ", created=" + created 
			+ ", productId=" + productId 
			+ ", quantity=" + quantity 
			+ ", price=" + price + "]";
	}

}
