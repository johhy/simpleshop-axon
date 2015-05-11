package com.github.johhy.simpleshopaxon.query.tables;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * The Class OrderTable.
 * <p>
 * 
 * @author johhy
 */
@Entity
public final class OrderTable {

	/** The id. */
	@Id
	@GeneratedValue
	private Long id;
	
	/** The order id. */
	private String orderId;
	
	/** The customer id. */
	private String customerId;
	
	/** The ship to. */
	private String shipTo;
	
	/** The order satus. */
	private String orderSatus;
	
	/** The total. */
	private Double total;
	
	/** The created. */
	private Date created;
	
	/** The product id. */
	private String productId;

	/** The quantity. */
	private Integer quantity;
	
	/** The price. */
	private Double price;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param newId the new id
	 */
	public void setId(final Long newId) {
		this.id = newId;
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
	 * Sets the order id.
	 *
	 * @param newOrderId the new order id
	 */
	public void setOrderId(final String newOrderId) {
		this.orderId = newOrderId;
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
	 * Sets the customer id.
	 *
	 * @param newCustomer the new customer id
	 */
	public void setCustomerId(final String newCustomer) {
		this.customerId = newCustomer;
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
	 * Sets the ship to.
	 *
	 * @param addressToShip the new ship to
	 */
	public void setShipTo(final String addressToShip) {
		this.shipTo = addressToShip;
	}

	/**
	 * Gets the order satus.
	 *
	 * @return the order satus
	 */
	public String getOrderSatus() {
		return orderSatus;
	}

	/**
	 * Sets the order satus.
	 *
	 * @param newOrderStatus the new order satus
	 */
	public void setOrderSatus(final String newOrderStatus) {
		this.orderSatus = newOrderStatus;
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
	 * Sets the total.
	 *
	 * @param newTotal the new total
	 */
	public void setTotal(final Double newTotal) {
		this.total = newTotal;
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
	 * Sets the created.
	 *
	 * @param newCreated the new created
	 */
	public void setCreated(final Date newCreated) {
		this.created = newCreated;
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
	 * Sets the product id.
	 *
	 * @param newProductId the new product id
	 */
	public void setProductId(final String newProductId) {
		this.productId = newProductId;
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
	 * Sets the quantity.
	 *
	 * @param newQuantity the new quantity
	 */
	public void setQuantity(final Integer newQuantity) {
		this.quantity = newQuantity;
	}

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * Sets the price.
	 *
	 * @param newPrice the new price
	 */
	public void setPrice(final Double newPrice) {
		this.price = newPrice;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OrderTable [id=" + id 
			+ ", orderId=" + orderId
			+ ", customerId=" + customerId 
			+ ", shipTo=" + shipTo
			+ ", orderSatus=" + orderSatus 
			+ ", total=" + total
			+ ", created=" + created 
			+ ", productId=" + productId 
			+ ", quantity=" + quantity 
			+ ", price=" + price + "]";
	}

}
