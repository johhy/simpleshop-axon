package com.github.johhy.simpleshopaxon.core.commands.productcell;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * 
 * @author johhy
 *
 */
public class RemoveProductCellCommand {
	
	@TargetAggregateIdentifier
	private final String codeOfProduct;

	public RemoveProductCellCommand(String codeOfProduct) {
		super();
		this.codeOfProduct = codeOfProduct;
	}

	public String getCodeOfProduct() {
		return codeOfProduct;
	}

	@Override
	public String toString() {
		return "RemoveProductCellCommand [codeOfProduct=" + codeOfProduct + "]";
	}
	
}
