package com.github.johhy.simpleshopaxon.core.commands.customer;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * @author johhy
 *
 */
public class RemoveCustomerCommand {
	
	@TargetAggregateIdentifier
	private final String customerId;

	public RemoveCustomerCommand(String customerId) {
		super();
		this.customerId = customerId;
	}

	public String getCustomerId() {
		return customerId;
	}

	@Override
	public String toString() {
		return "RemoveCustomerCommand [customerId=" + customerId + "]";
	}
	

}
