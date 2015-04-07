package com.github.johhy.simpleshopaxon.core.commands.productcell;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * @author johhy
 *
 */
public class ChangePriceOfProductCommand {
	
	@TargetAggregateIdentifier
	private final String codeOfProduct;
	private final double price;
	
	public ChangePriceOfProductCommand(String codeOfProduct, double price) {
		super();
		this.codeOfProduct = codeOfProduct;
		this.price = price;
	}

	public String getCodeOfProduct() {
		return codeOfProduct;
	}

	public double getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "ChangePriceOfProductCommand [codeOfProduct=" + codeOfProduct
				+ ", price=" + price + "]";
	}
	
}
