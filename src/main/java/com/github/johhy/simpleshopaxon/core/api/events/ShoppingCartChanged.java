package com.github.johhy.simpleshopaxon.core.api.events;

import java.util.List;

import com.github.johhy.simpleshopaxon.core.api.shared.Product;

/**
 * The Class ShoppingCartChanged.
 * 
 * @author johhy
 */
public class ShoppingCartChanged extends AbstractCustomerEvent {

	/** The products. */
	private final List<Product> products;
	
	/**
	 * Instantiates a new shopping cart changed.
	 *
	 * @param customerId the customer id
	 * @param productsInShoppingCart the products in shopping cart
	 */
	public ShoppingCartChanged(final String customerId,
		final List<Product> productsInShoppingCart) {
		super(customerId);
		this.products = productsInShoppingCart;
	}

	/**
	 * Gets the products.
	 *
	 * @return the products
	 */
	public final List<Product> getProducts() {
		return products;
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.api.events
	 * .AbstractCustomerEvent#toString()
	 */
	@Override
	public final String toString() {
		return "ShoppingCartChanged [products=" + products 
			+ ", customerId=" + getCustomerId() + "]";
	}

}
