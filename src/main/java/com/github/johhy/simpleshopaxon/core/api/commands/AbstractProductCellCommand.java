package com.github.johhy.simpleshopaxon.core.api.commands;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotBlank;

import com.github.johhy.simpleshopaxon.core.api.shared.Product;
import com.github.johhy.simpleshopaxon.core.infra.Find;
import com.github.johhy.simpleshopaxon.query.repository.ProductTableRepository;

/**
 * The Class ProductCellCommand.
 * 
 * @author johhy
 */
public abstract class AbstractProductCellCommand {

	/** The product id. */
	@NotBlank
	@Find(repository = ProductTableRepository.class,
		methodName = "findByProductId", mustExists=true)
	@TargetAggregateIdentifier
	private final String productId;

	/**
	 * Instantiates a new product cell command.
	 *
	 * @param product the product
	 */
	public AbstractProductCellCommand(final Product product) {
		super();
		this.productId = product.getProductId();
	}

	/**
	 * Gets the product id.
	 *
	 * @return the product id
	 */
	public final String getProductId() {
		return productId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public abstract String toString();
	
}
