package com.github.johhy.simpleshopaxon.core.api.commands;

import com.github.johhy.simpleshopaxon.core.api.shared.Product;

/**
 * The Class AddProductToProductCell.
 * 
 * @author johhy
 */
public class AddProductToProductCell extends ProductChangeInProductCell {

	/**
	 * Instantiates a new adds the product to product cell.
	 *
	 * @param product the product
	 */
	public AddProductToProductCell(final Product product) {
		super(product);
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.api.commands
	 * 	.ProductChangeInProductCell#toString()
	 */
	@Override
	public final String toString() {
		return "AddProductToProductCell [product=" + getProduct() + "]";
	}

}
