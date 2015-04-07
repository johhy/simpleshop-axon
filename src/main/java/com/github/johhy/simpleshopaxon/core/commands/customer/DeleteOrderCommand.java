package com.github.johhy.simpleshopaxon.core.commands.customer;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * @author johhy
 *
 */
public class DeleteOrderCommand {
	
	@TargetAggregateIdentifier
	private final String customerId;
	
	private final String orderId;
	
	public DeleteOrderCommand(String customerId, String orderId) {
		super();
		this.customerId = customerId;
		this.orderId = orderId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public String getOrderId() {
		return orderId;
	}
	
	@Override
	public String toString() {
		return "DeleteOrderCommand [customerId=" + customerId + ", orderId="
				+ orderId + "]";
	}
	
}
