package com.github.johhy.simpleshopaxon.core.events.productcell;

/**
 * @author johhy
 *
 */
public class ReservedProductChangedInProductCellEvent {
	
	private final String codeOfProduct;
	private final String orderId;
	private final int amount;
	
	public ReservedProductChangedInProductCellEvent(String codeOfProduct,
			String orderId, int amount) {
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
		return "ReservedProductChangedInProductCellEvent [codeOfProduct="
				+ codeOfProduct + ", orderId=" + orderId + ", amount=" + amount
				+ "]";
	}
	
}
