package com.github.johhy.simpleshopaxon.core.api.events;

import java.util.Map;

import com.github.johhy.simpleshopaxon.core.api.shared.Product;

/**
 * The Class AbstractReservedProductChanged.
 * 
 * @author johhy
 */
public abstract class AbstractReservedProductChanged 
	extends AbstractProductCellEvent {

	/** The customer id. */
	private final String customerId;
	
	/** The reserved. */
	private final Map<String, Integer> reserved;

	/**
	 * Instantiates a new abstract reserved product changed.
	 *
	 * @param product the product
	 * @param customerIdForReservedProductChanged 
	 * 	  the customer id for reserved product changed
	 * @param newStateReservedProducts the new state reserved products
	 */
	public AbstractReservedProductChanged(final Product product, 
		final String customerIdForReservedProductChanged, 
		final Map<String, Integer> newStateReservedProducts) {
		super(product);
		this.customerId = customerIdForReservedProductChanged;
		this.reserved = newStateReservedProducts;
	}

	/**
	 * Gets the customer id.
	 *
	 * @return the customer id
	 */
	public final String getCustomerId() {
		return customerId;
	}

	/**
	 * Gets the reserved.
	 *
	 * @return the reserved
	 */
	public final Map<String, Integer> getReserved() {
		return reserved;
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.api.events
	 * .AbstractProductCellEvent#toString()
	 */
	@Override
	public abstract String toString();	
	
}
