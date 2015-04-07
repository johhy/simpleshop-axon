package com.github.johhy.simpleshopaxon.core.events.customer;

/**
 * @author johhy
 *
 */
public class CustomerRemovedEvent {
	
	private final String customerId;

	public CustomerRemovedEvent(String customerId) {
		super();
		this.customerId = customerId;
	}

	public String getCustomerId() {
		return customerId;
	}

	@Override
	public String toString() {
		return "CustomerRemovedEvent [customerId=" + customerId + "]";
	}

}
