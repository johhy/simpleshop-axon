package com.github.johhy.simpleshopaxon.core.events.customer;

/**
 * @author johhy
 *
 */
public class CustomerInfoChangedEvent {
	
	private final String customerId;
	
	private final String customerName;
	
	private final String customerLogin;

	public CustomerInfoChangedEvent(String customerId, String customerName,
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
		return "CustomerInfoChangedEvent [customerId=" + customerId
				+ ", customerName=" + customerName + ", customerLogin="
				+ customerLogin + "]";
	}
	
}
