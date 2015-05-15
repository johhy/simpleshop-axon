package com.github.johhy.simpleshopaxon.core.api.commands;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotBlank;

import com.github.johhy.simpleshopaxon.core.api.shared.Product;
import com.github.johhy.simpleshopaxon.core.infra.Find;
import com.github.johhy.simpleshopaxon.query.repository.ProductTableRepository;

/**
 * The Class CreateProductCell.
 * 
 * @author johhy
 */
public class CreateProductCell {

	/** The product id. */
	@NotBlank
	@Find(repository = ProductTableRepository.class,
		methodName = "findByProductId", mustExists=false)
	@TargetAggregateIdentifier
	private final String productId;
	
	private final Product product;
	/**
	 * Instantiates a new creates the product cell.
	 *
	 * @param product the product
	 */
	public CreateProductCell(final Product product) {
		this.productId = product.getProductId();
		this.product = product;
	}
	/**
	 * @return the productId
	 */
	public String getProductId() {
	    return productId;
	}
	/**
	 * @return the product
	 */
	public Product getProduct() {
	    return product;
	}



}
