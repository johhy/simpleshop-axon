package com.github.johhy.simpleshopaxon.core.api.commands;

import javax.validation.constraints.Min;

import com.github.johhy.simpleshopaxon.core.api.shared.Product;

/**
 * The Class ChangeCapacityProductCell.
 * 
 * @author johhy
 */
public class ChangeCapacityProductCell extends AbstractProductCellCommand {

	/** The capacity. */
	@Min(1)
	private final Integer capacity;
	
	/**
	 * Instantiates a new change capacity product cell.
	 *
	 * @param product the product
	 * @param newCapacity the capacity
	 */
	public ChangeCapacityProductCell(final Product product, 
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
	 * @see com.github.johhy.simpleshopaxon.core.api.commands
	 * .ProductCellCommand#toString()
	 */
	@Override
	public final String toString() {
		return "ChangeCapacityProductCell [capacity=" 
			+ capacity + ", productId=" + getProductId() + "]";
	}

}
