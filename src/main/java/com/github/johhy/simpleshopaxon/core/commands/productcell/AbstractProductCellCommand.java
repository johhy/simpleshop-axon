package com.github.johhy.simpleshopaxon.core.commands.productcell;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * @author johhy
 *
 */
public class AbstractProductCellCommand {
	@TargetAggregateIdentifier
	private final String codeOfProduct;

	public AbstractProductCellCommand(String codeOfProduct) {
		super();
		this.codeOfProduct = codeOfProduct;
	}

	public String getCodeOfProduct() {
		return codeOfProduct;
	}
	
}
