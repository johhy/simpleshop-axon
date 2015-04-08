package com.github.johhy.simpleshopaxon.core.commands.productcell;

/**
 * @author johhy
 *
 */
public class ReturnAmountProductFromOrderToProductCellCommand 
	extends AbstractProductCellCommand {

	private final String orderId;
	private final int amount;
	
	public ReturnAmountProductFromOrderToProductCellCommand(
			String codeOfProduct, String orderId, int amount) {
		super(codeOfProduct);
		this.orderId = orderId;
		this.amount = amount;
	}

	public String getOrderId() {
		return orderId;
	}

	public int getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "ReturnAmountProductFromOrderToProductCellCommand [orderId=" + orderId + ", amount=" + amount
				+ ", codeOfProduct=" + getCodeOfProduct() + "]";
	}


	
}
