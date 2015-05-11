package com.github.johhy.simpleshopaxon.core.api.commands;

import com.github.johhy.simpleshopaxon.core.api.shared.Product;

/**
 * The Class CreateProductCell.
 * 
 * @author johhy
 */
public class CreateProductCell extends ProductChangeInProductCell {

	/**
	 * Instantiates a new creates the product cell.
	 *
	 * @param product the product
	 */
	public CreateProductCell(final Product product) {
		super(product);
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.api.commands
	 * .ProductChangeInProductCell#toString()
	 */
	@Override
	public final String toString() {
		return "CreateProductCell [product=" + getProduct() + "]";
	}


}
