package com.github.johhy.simpleshopaxon.core.api.events;

import com.github.johhy.simpleshopaxon.core.api.shared.Product;

/**
 * The Class ProductCellCreated.
 * 
 * @author johhy
 */
public class ProductCellCreated extends AbstractProductCellEvent {
	
	/** The capacity. */
	private final Integer capacity;
	
	/**
	 * Instantiates a new product cell created.
	 *
	 * @param product the product
	 * @param capacityCreatedProductCell the capacity created product cell
	 */
	public ProductCellCreated(final Product product, 
		final Integer capacityCreatedProductCell) {
		super(product);
		this.capacity = capacityCreatedProductCell;
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
		return "ProductCellCreated [capacity=" + capacity 
				+ ", productId=" + getProductId() 
				+ ", product=" + getProduct() + "]";
	}
	
}
