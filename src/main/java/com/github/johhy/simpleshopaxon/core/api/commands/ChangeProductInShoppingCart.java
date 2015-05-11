package com.github.johhy.simpleshopaxon.core.api.commands;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.github.johhy.simpleshopaxon.core.api.shared.Product;

/**
 * The Class ChangeProductInShoppingCart.
 * 
 * @author johhy
 */
public class ChangeProductInShoppingCart extends CustomerCommand {

	/** The product. */
	@NotNull
	@Valid
	private final Product product;
	
	/**
	 * Instantiates a new change product in shopping cart.
	 *
	 * @param customerId the customer id
	 * @param newProduct the new product
	 */
	public ChangeProductInShoppingCart(final String customerId, 
		final Product newProduct) {
		super(customerId);
		this.product = newProduct;
	}

	/**
	 * Gets the product.
	 *
	 * @return the product
	 */
	public final Product getProduct() {
		return product;
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.api.commands
	 * .CustomerCommand#toString()
	 */
	@Override
	public final String toString() {
		return "ChangeProductInShoppingCart [product=" 
			+ product + ", customerId=" + getCustomerId() + "]";
	}

}
