package com.github.johhy.simpleshopaxon.core.commands.productcell;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * @author johhy
 *
 */
public class CreateProductCellCommand {
	
	@TargetAggregateIdentifier
	private final String codeOfProduct;
	
	public CreateProductCellCommand(String codeOfProduct) {
		super();
		this.codeOfProduct = codeOfProduct;
	}

	public String getCodeOfProduct() {
		return codeOfProduct;
	}

	@Override
	public String toString() {
		return "CreateProductCellCommand [codeOfProduct=" + codeOfProduct + "]";
	}



}
