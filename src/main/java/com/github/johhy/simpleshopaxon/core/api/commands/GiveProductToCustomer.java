package com.github.johhy.simpleshopaxon.core.api.commands;

import org.hibernate.validator.constraints.NotBlank;

import com.github.johhy.simpleshopaxon.core.api.shared.Product;

/**
 * The Class GiveProductToCustomer.
 * 
 * @author johhy
 */
public class GiveProductToCustomer extends ProductChangeInProductCell {

	/** The customer id. */
	@NotBlank
	private final String customerId;
	
	/**
	 * Instantiates a new give product to customer.
	 *
	 * @param product the product
	 * @param customerIdReceiverProduct the customer id receiver product
	 */
	public GiveProductToCustomer(final Product product, 
		final String customerIdReceiverProduct) {
		super(product);
		this.customerId = customerIdReceiverProduct;
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
		return "GiveProductToCustomer [customerId=" + customerId 
				+ ", product=" + getProduct()
				+ ", productId=" + getProductId() + "]";
	}
	

}
