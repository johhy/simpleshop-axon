package com.github.johhy.simpleshopaxon.core.api.commands;

import org.hibernate.annotations.NotFound;

import com.github.johhy.simpleshopaxon.core.api.shared.Product;

/**
 * The Class ReturnProductFromCustomer.
 * 
 * @author johhy
 */
public class ReturnProductFromCustomer extends ProductChangeInProductCell {

	/** The customer id. */
	@NotFound
	private final String customerId;

	/**
	 * Instantiates a new return product from customer.
	 *
	 * @param product the product
	 * @param customerIdReturnProduct the customer id return product
	 */
	public ReturnProductFromCustomer(final Product product, 
		final String customerIdReturnProduct) {
		super(product);
		this.customerId = customerIdReturnProduct;
	}

	/**
	 * Gets the customer id.
	 *
	 * @return the customer id
	 */
	public final String getCustomerId() {
		return customerId;
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.api.commands
	 * .ProductChangeInProductCell#toString()
	 */
	@Override
	public final String toString() {
		return "ReturnProductFromCustomer [customerId=" + customerId 
				+ ", product=" + getProduct()
				+ ", productId=" + getProductId() + "]";
	}

	
}
