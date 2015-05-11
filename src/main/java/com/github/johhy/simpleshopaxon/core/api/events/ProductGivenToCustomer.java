package com.github.johhy.simpleshopaxon.core.api.events;

import java.util.Map;

import com.github.johhy.simpleshopaxon.core.api.shared.Product;

/**
 * The Class ProductGivenToCustomer.
 * 
 * @author johhy
 */
public class ProductGivenToCustomer extends AbstractReservedProductChanged {

	/** The given product. */
	private final Product givenProduct;

	/**
	 * Instantiates a new product given to customer.
	 *
	 * @param product the product
	 * @param customerId the customer id
	 * @param reserved the reserved
	 * @param productGivenForCustomer the product given for customer
	 */
	public ProductGivenToCustomer(final Product product, 
		final String customerId, 
		final Map<String, Integer> reserved,
		final Product productGivenForCustomer) {
		super(product, customerId, reserved);
		this.givenProduct = productGivenForCustomer;
	}

	
	/**
	 * Gets the given product.
	 *
	 * @return the given product
	 */
	public final Product getGivenProduct() {
		return givenProduct;
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.api.events
	 * .AbstractReservedProductChanged#toString()
	 */
	@Override
	public final String toString() {
		return "ProductGivenToCustomer [givenProduct=" + givenProduct 
				+ ", customerId=" + getCustomerId()
				+ ", reserved=" + getReserved()
				+ ", productId=" + getProductId() 
				+ ", product=" + getProduct() + "]";
	}

}
