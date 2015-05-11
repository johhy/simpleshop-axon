package com.github.johhy.simpleshopaxon.core.api.events;

import com.github.johhy.simpleshopaxon.core.api.shared.Product;

/**
 * The Class ProductPriceInProductCellChanged.
 * 
 * @author johhy
 */
public class ProductPriceInProductCellChanged 
	extends AbstractProductInProductCellChanged {

	/**
	 * Instantiates a new product price in product cell changed.
	 *
	 * @param product the product
	 */
	public ProductPriceInProductCellChanged(final Product product) {
		super(product);
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.api.events
	 * .AbstractProductInProductCellChanged#toString()
	 */
	@Override
	public final String toString() {
		return "ProductPriceInProductCellChanged [productId=" + getProductId()
				+ " ,product=" + getProduct() + "]";
	}
	

}
