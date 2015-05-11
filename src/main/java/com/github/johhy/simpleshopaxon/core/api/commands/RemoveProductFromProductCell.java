package com.github.johhy.simpleshopaxon.core.api.commands;

import com.github.johhy.simpleshopaxon.core.api.shared.Product;

/**
 * The Class RemoveProductFromProductCell.
 * 
 * @author johhy
 */
public class RemoveProductFromProductCell extends ProductChangeInProductCell {

	/**
	 * Instantiates a new removes the product from product cell.
	 *
	 * @param product the product
	 */
	public RemoveProductFromProductCell(final Product product) {
		super(product);
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.api.commands
	 * .ProductChangeInProductCell#toString()
	 */
	@Override
	public final String toString() {
		return "RemoveProductFromProductCell [product()="
				+ getProduct() + "]";
	}


}
