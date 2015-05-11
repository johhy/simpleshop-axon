package com.github.johhy.simpleshopaxon.web.rest.dto;

/**
 * The Class Product.
 * <p>
 * 
 * @author johhy
 */
public final class Product {
	
	/** The product id. */
	private final String productId;

	/** The quantity. */
	private final Integer quantity;
	
	/** The price. */
	private final Double price;

	/**
	 * Instantiates a new product.
	 *
	 * @param newProductId the new product id
	 * @param newQuantity the new quantity
	 * @param newPrice the new price
	 */
	public Product(final String newProductId, final Integer newQuantity, 
		final Double newPrice) {
		super();
		this.productId = newProductId;
		this.quantity = newQuantity;
		this.price = newPrice;
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
		return "Product [productId=" + productId 
			+ ", quantity=" + quantity 
			+ ", price=" + price + "]";
	}
	

}
