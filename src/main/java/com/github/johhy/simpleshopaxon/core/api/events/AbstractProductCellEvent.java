package com.github.johhy.simpleshopaxon.core.api.events;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import com.github.johhy.simpleshopaxon.core.api.shared.Product;

/**
 * The Class AbstractProductCellEvent.
 * 
 * @author johhy
 */
public abstract class AbstractProductCellEvent {

	/** The product id. */
	@TargetAggregateIdentifier
	private final String productId;
	
	/** The product. */
	private final Product product;

	/**
	 * Instantiates a new abstract product cell event.
	 *
	 * @param productDoEvent the product do event
	 */
	public AbstractProductCellEvent(final Product productDoEvent) {
		super();
		this.productId = productDoEvent.getProductId();
		this.product = productDoEvent;
	}

	/**
	 * Gets the product id.
	 *
	 * @return the product id
	 */
	public final String getProductId() {
		return productId;
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
	 * @see java.lang.Object#toString()
	 */
	@Override
	public abstract String toString();

}
