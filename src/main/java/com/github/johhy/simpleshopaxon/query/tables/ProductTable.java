package com.github.johhy.simpleshopaxon.query.tables;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * The Class ProductTable.
 * <p>
 * 
 * @author johhy
 */
@Entity
public final class ProductTable {

	/** The id. */
	@Id
	@GeneratedValue
	private Long id;
	
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
		return "ProductTable [id=" + id 
			+ ", productId=" + productId 
			+ ", quantity=" + quantity 
			+ ", price=" + price + "]";
	}

}
