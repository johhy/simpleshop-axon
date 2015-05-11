package com.github.johhy.simpleshopaxon.core.api.commands;

import com.github.johhy.simpleshopaxon.core.api.shared.Product;

/**
 * The Class ChangePriceInProductCell.
 * 
 * @author johhy
 */
public class ChangePriceInProductCell extends ProductChangeInProductCell {

	/**
	 * Instantiates a new change price in product cell.
	 *
	 * @param product the product
	 */
	public ChangePriceInProductCell(final Product product) {
		super(product);
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.api.commands
	 * .ProductChangeInProductCell#toString()
	 */
	@Override
	public final String toString() {
		return "ChangePriceInProductCell [product=" + getProduct() + "]";
	}

}
