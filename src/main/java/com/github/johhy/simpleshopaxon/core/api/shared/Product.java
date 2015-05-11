package com.github.johhy.simpleshopaxon.core.api.shared;


import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.jcabi.aspects.Loggable;

/**
 * The Class Product.
 * <p>
 * Domain object.Value object.
 * Keeps product.
 * Have methods to change product for
 * example quantity.
 * When quantity is changed it return new instance of
 * product. 
 * 
 * @author johhy
 */
public class Product {

	/** The product id. */
	@NotBlank
	private final String productId;

	/** The quantity. */
	@Min(0)
	private final Integer quantity;
	
	/** The price. */
	@NotNull
	@Valid
	private final Price price;

	/**
	 * Instantiates a new product.
	 *
	 * @param productIdCreatedProduct the product id created product
	 * @param quantityCreatedProduct the quantity created product
	 * @param priceCreatedProduct the price created product
	 */
	public Product(final String productIdCreatedProduct, 
		final Integer quantityCreatedProduct, 
		final Price priceCreatedProduct) {
		super();
		this.productId = productIdCreatedProduct;
		this.quantity = quantityCreatedProduct;
		this.price = priceCreatedProduct;
	}

	/**
	 * Adds the amount.
	 *
	 * @param productToAdd the product to add
	 * @return the new product with new quantity
	 */
	@Loggable(trim = false, prepend = false, value = Loggable.DEBUG)
	public final Product addAmount(final Product productToAdd) {
		return new Product(productToAdd.getProductId(),
				quantity + productToAdd.getQuantity(),
				new Price(price.getValue()));
	}
	
	/**
	 * Removes the amount.
	 *
	 * @param productToRemove the product to remove
	 * @return the new product with new quantity
	 */
	@Loggable(trim = false, prepend = false, value = Loggable.DEBUG)
	public final Product removeAmount(final Product productToRemove) {
		return new Product(productToRemove.getProductId(),
				quantity - productToRemove.getQuantity(),
				new Price(price.getValue()));
	}
	
	/**
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	public final Integer getQuantity() {
		return quantity;
	}
	
	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public final Price getPrice() {
		return price;
	}

	/**
	 * Gets the product id.
	 *
	 * @return the product id
	 */
	public final String getProductId() {
		return productId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		int hashPrice = 0;
		if (price != null) {
		    hashPrice = price.hashCode();
		}
		int hashProductId = 0;
		if (productId != null) {
		    hashProductId = productId.hashCode();
		}
		int hashQuantity = 0;
		if (quantity != null) {
		    hashQuantity = quantity.hashCode();
		}
		result = prime * result + hashPrice;
		result = prime * result + hashProductId;
		result = prime * result + hashQuantity;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals(final Object obj) {
		if (this == obj) {
		    return true;
		}
		if (obj == null) {
		    return false;
		}
		if (!(obj instanceof Product)) {
		    return false;
		}
		Product other = (Product) obj;
		if (price == null) {
			if (other.price != null) {
			    return false;
			}
		} else if (!price.equals(other.price)) {
		    return false;
		}
		if (productId == null) {
			if (other.productId != null) {
			    return false;
			}
		} else if (!productId.equals(other.productId)) {
		    return false;
		}
		if (quantity == null) {
			if (other.quantity != null) {
			    return false;
			}
		} else if (!quantity.equals(other.quantity)) {
		    return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		return "Product [productId=" + productId 
				+ ", quantity=" + quantity 
				+ ", price=" + price + "]";
	}
	
}
