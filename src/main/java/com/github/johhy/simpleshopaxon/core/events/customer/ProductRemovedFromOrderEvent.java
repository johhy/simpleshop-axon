package com.github.johhy.simpleshopaxon.core.events.customer;

public class ProductRemovedFromOrderEvent {
	
	private final String customerId;
	
	private final String orderId;
	
	private final String codeOfProduct;
	
	public ProductRemovedFromOrderEvent(
			String customerId, 
			String orderId,
			String codeOfProduct) {
		super();
		this.customerId = customerId;
		this.orderId = orderId;
		this.codeOfProduct = codeOfProduct;
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

	@Override
	public String toString() {
		return "ProductRemovedFromOrderEvent [customerId=" + customerId
				+ ", orderId=" + orderId + ", codeOfProduct=" + codeOfProduct
				+ "]";
	}
	
}
