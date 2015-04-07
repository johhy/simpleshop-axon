package com.github.johhy.simpleshopaxon.core.commands.customer;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * @author johhy
 *
 */
public class ChangeCustomerInfoCommand {

	@TargetAggregateIdentifier
	private final String customerId;
	
	private final String customerName;
	
	private final String customerLogin;

	public ChangeCustomerInfoCommand(String customerId, String customerName,
			String customerLogin) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerLogin = customerLogin;
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getCustomerLogin() {
		return customerLogin;
	}

	@Override
	public String toString() {
		return "ChangeCustomerInfoCommand [customerId=" + customerId
				+ ", customerName=" + customerName + ", customerLogin="
				+ customerLogin + "]";
	}
	
}
