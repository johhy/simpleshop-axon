package com.github.johhy.simpleshopaxon.core.commands.customer;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * @author johhy
 *
 */
public class RemoveProductFromOrderCommand {
	
	@TargetAggregateIdentifier
	private final String customerId;
	
	private final String orderId;
	
	private final String codeOfProduct;
	
	private final int amountToRemove;
	
	public RemoveProductFromOrderCommand(String customerId, String orderId,
			String codeOfProduct, int amountToRemove) {
		super();
		this.customerId = customerId;
		this.orderId = orderId;
		this.codeOfProduct = codeOfProduct;
		this.amountToRemove = amountToRemove;
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getCodeOfProduct() {
		return codeOfProduct;
	}

	public int getAmountToRemove() {
		return amountToRemove;
	}


}
