package com.github.johhy.simpleshopaxon.core.events.customer;

/**
 * @author johhy
 *
 */
public class OrderCreatedEvent  {

	private final String customerId;
	
	private final String orderId;
	
	public String getCustomerId() {
		return customerId;
	}

	public String getOrderId() {
		return orderId;
	}

	public OrderCreatedEvent(String customerId, String orderId) {
		this.customerId = customerId;
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "OrderCreatedEvent [customerId=" + customerId + ", orderId="
				+ orderId + "]";
	}
	
	
}
