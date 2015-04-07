package com.github.johhy.simpleshopaxon.core.commands.productcell;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * @author johhy
 *
 */
public class ReturnAmountProductFromOrderToProductCellCommand {

	@TargetAggregateIdentifier
	private final String codeOfProduct;
	private final String orderId;
	private final int amount;
	
	public ReturnAmountProductFromOrderToProductCellCommand(
			String codeOfProduct, String orderId, int amount) {
		super();
		this.codeOfProduct = codeOfProduct;
		this.orderId = orderId;
		this.amount = amount;
	}

	public String getCodeOfProduct() {
		return codeOfProduct;
	}

	public String getOrderId() {
		return orderId;
	}

	public int getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "ReturnAmountProductFromOrderToProductCellCommand [codeOfProduct="
				+ codeOfProduct
				+ ", orderId="
				+ orderId
				+ ", amount="
				+ amount
				+ "]";
	}
	
}
