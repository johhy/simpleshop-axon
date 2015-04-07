package com.github.johhy.simpleshopaxon.core.commands.customer;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * @author johhy
 *
 */
public class AddProductToOrderCommand {
	
	@TargetAggregateIdentifier
	private final String customerId;
	
	private final String orderId;
	
	private final String codeOfProduct;
	
	private final int quantity;
	
	private final double price;
	
	public AddProductToOrderCommand(String customerId, String orderId,
			String codeOfProduct, int quantity, double price) {
		super();
		this.customerId = customerId;
		this.orderId = orderId;
		this.codeOfProduct = codeOfProduct;
		this.quantity = quantity;
		this.price = price;
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
	
	public double getPrice() {
		return price;
	}
	
	public int getQuantity() {
		return quantity;
	}

	@Override
	public String toString() {
		return "AddProductToOrderCommand [customerId=" + customerId
				+ ", orderId=" + orderId + ", codeOfProduct=" + codeOfProduct
				+ ", quantity=" + quantity + ", price=" + price + "]";
	}
	
}
