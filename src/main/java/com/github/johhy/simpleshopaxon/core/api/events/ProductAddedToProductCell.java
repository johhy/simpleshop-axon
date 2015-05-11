package com.github.johhy.simpleshopaxon.core.api.events;

import com.github.johhy.simpleshopaxon.core.api.shared.Product;

/**
 * The Class ProductAddedToProductCell.
 * 
 * @author johhy
 */
public class ProductAddedToProductCell 
	extends AbstractProductInProductCellChanged {

	/**
	 * Instantiates a new product added to product cell.
	 *
	 * @param productAdded the product added
	 */
	public ProductAddedToProductCell(final Product productAdded) {
		super(productAdded);
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.api.events
	 * .AbstractProductInProductCellChanged#toString()
	 */
	@Override
	public final String toString() {
		return "ProductAddedToProductCell [productId=" + getProductId() 
				+ ", product=" + getProduct() + "]";
	}
	
}
