package com.github.johhy.simpleshopaxon.core.commands.productcell;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * @author johhy
 *
 */
public class AddProductToProductCellCommand {
	
	@TargetAggregateIdentifier
	private final String codeOfProduct;
	private final int quantity;
	
	public AddProductToProductCellCommand(String codeOfProduct, int quantity) {
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
		return "AddProductToProductCellCommand [codeOfProduct=" + codeOfProduct
				+ ", quantity=" + quantity + "]";
	}
	
}
