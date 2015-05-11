package com.github.johhy.simpleshopaxon.query.tables;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * The Class CustomerTable.
 * <p>
 * 
 * @author johhy
 */
@Entity
public final class CustomerTable {

	/** The id. */
	@Id
	@GeneratedValue
	private Long id;
	
	/** The customer id. */
	private String customerId;
	
	/** The address. */
	private String address;
	
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
	 * @param newCustomerId the new customer id
	 */
	public void setCustomerId(final String newCustomerId) {
		this.customerId = newCustomerId;
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
	 * Sets the address.
	 *
	 * @param newAddress the new address
	 */
	public void setAddress(final String newAddress) {
		this.address = newAddress;
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
	 * @param newCreatdDate the new created
	 */
	public void setCreated(final Date newCreatdDate) {
		this.created = newCreatdDate;
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
		return "CustomerTable [id=" + id 
			+ ", customerId=" + customerId 
			+ ", address=" + address 
			+ ", created=" + created 
			+ ", productId=" + productId
			+ ", quantity=" + quantity 
			+ ", price=" + price + "]";
	}
	
	

	
}
