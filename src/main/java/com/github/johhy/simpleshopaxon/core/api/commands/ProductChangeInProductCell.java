package com.github.johhy.simpleshopaxon.core.api.commands;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.github.johhy.simpleshopaxon.core.api.shared.Product;

/**
 * The Class ProductChangeInProductCell.
 * 
 * @author johhy
 */
public abstract class ProductChangeInProductCell 
	extends AbstractProductCellCommand {
	
	/** The product. */
	@NotNull
	@Valid
	private final Product product;
	
	/**
	 * Instantiates a new product change in product cell.
	 *
	 * @param changedProduct the changed product
	 */
	public ProductChangeInProductCell(final Product changedProduct) {
		super(changedProduct);
		this.product = changedProduct;
	}

	/**
	 * Gets the product.
	 *
	 * @return the product
	 */
	public final Product getProduct() {
		return product;
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.api.commands
	 * .ProductCellCommand#toString()
	 */
	@Override
	public abstract String toString();


}
