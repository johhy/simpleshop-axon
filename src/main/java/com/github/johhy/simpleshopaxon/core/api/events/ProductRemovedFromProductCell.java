package com.github.johhy.simpleshopaxon.core.api.events;

import com.github.johhy.simpleshopaxon.core.api.shared.Product;

/**
 * The Class ProductRemovedFromProductCell.
 * 
 * @author johhy
 */
public class ProductRemovedFromProductCell 
	extends AbstractProductInProductCellChanged {

	/**
	 * Instantiates a new product removed from product cell.
	 *
	 * @param productRemoved the product removed
	 */
	public ProductRemovedFromProductCell(final Product productRemoved) {
		super(productRemoved);
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.api.events
	 * .AbstractProductInProductCellChanged#toString()
	 */
	@Override
	public final String toString() {
		return "ProductRemovedFromProductCell [productId=" + getProductId() 
				+ ", product=" + getProduct() + "]";
	}

}
