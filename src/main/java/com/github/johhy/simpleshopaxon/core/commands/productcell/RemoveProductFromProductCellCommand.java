package com.github.johhy.simpleshopaxon.core.commands.productcell;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * @author johhy
 *
 */
public class RemoveProductFromProductCellCommand {
	
	@TargetAggregateIdentifier
	private final String codeOfProduct;
	private final int quantity;
	
	public RemoveProductFromProductCellCommand(String codeOfProduct, int quantity) {
		super();
		this.codeOfProduct = codeOfProduct;
		this.quantity = quantity;
	}

	public String getCodeOfProduct() {
		return codeOfProduct;
	}

	public int getQuantity() {
		return quantity;
	}

	@Override
	public String toString() {
		return "RemoveProductFromProductCellCommand [codeOfProduct="
				+ codeOfProduct + ", quantity=" + quantity + "]";
	}
	
}
