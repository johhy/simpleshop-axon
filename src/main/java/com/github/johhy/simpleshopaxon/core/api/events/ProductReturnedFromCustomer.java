package com.github.johhy.simpleshopaxon.core.api.events;

import java.util.Map;

import com.github.johhy.simpleshopaxon.core.api.shared.Product;

/**
 * The Class ProductReturnedFromCustomer.
 * 
 * @author johhy
 */
public class ProductReturnedFromCustomer 
	extends AbstractReservedProductChanged {

	/**
	 * Instantiates a new product returned from customer.
	 *
	 * @param product the product
	 * @param customerId the customer id
	 * @param reserved the reserved
	 */
	public ProductReturnedFromCustomer(final Product product, 
		final String customerId, 
		final Map<String, Integer> reserved) {
		super(product, customerId, reserved);
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.api.events
	 * .AbstractReservedProductChanged#toString()
	 */
	@Override
	public final String toString() {
		return "ProductReturnedFromCustomer [customerId=" + getCustomerId()
				+ ", reserved=" + getReserved()
				+ ", productId=" + getProductId() 
				+ ", pProduct=" + getProduct() + "]";
	}


}
