package com.github.johhy.simpleshopaxon.core.events.customer;


/**
 * @author johhy
 *
 */
public class OrderDeletedEvent {

	private final String customerId;
	
	private final String orderId;
	
	public OrderDeletedEvent(String customerId, String orderId) {
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
		return "OrderDeletedEvent [customerId=" + customerId + ", orderId="
				+ orderId + "]";
	}
	
}
