package com.github.johhy.simpleshopaxon.core.api.events;

import com.github.johhy.simpleshopaxon.core.api.shared.Product;

/**
 * The Class AbstractProductInProductCellChanged.
 * 
 * @author johhy
 */
public abstract class AbstractProductInProductCellChanged 
	extends AbstractProductCellEvent {

	/**
	 * Instantiates a new abstract product in product cell changed.
	 *
	 * @param product the product
	 */
	public AbstractProductInProductCellChanged(final Product product) {
		super(product);
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.api.events
	 * .AbstractProductCellEvent#toString()
	 */
	@Override
	public abstract String toString();
}
