package com.github.johhy.simpleshopaxon.core.api.events;

import com.github.johhy.simpleshopaxon.core.api.shared.Product;

/**
 * The Class CapacityProductCellChanged.
 * 
 * @author johhy
 */
public class CapacityProductCellChanged extends AbstractProductCellEvent {

	/** The capacity. */
	private final Integer capacity;
	
	/**
	 * Instantiates a new capacity product cell changed.
	 *
	 * @param product the product
	 * @param newCapacity the new capacity
	 */
	public CapacityProductCellChanged(final Product product, 
		final Integer newCapacity) {
		super(product);
		this.capacity = newCapacity;
	}

	/**
	 * Gets the capacity.
	 *
	 * @return the capacity
	 */
	public final Integer getCapacity() {
		return capacity;
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.api.events
	 * .AbstractProductCellEvent#toString()
	 */
	@Override
	public final String toString() {
		return "CapacityProductCellChanged [capacity=" + capacity 
				+ ", productId=" + getProductId()
				+ ", product=" + getProduct() + "]";
	}

}
