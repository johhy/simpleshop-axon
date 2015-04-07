package com.github.johhy.simpleshopaxon.query.customer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author johhy
 *
 */
@Entity
public class CustomerTable {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String customerId;
	
	private String orderId;
	
	private String codeOfProduct;
	
	private int quantity;
	
	private double price;
	
	public CustomerTable() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCodeOfProduct() {
		return codeOfProduct;
	}

	public void setCodeOfProduct(String codeOfProduct) {
		this.codeOfProduct = codeOfProduct;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "CustomerTable [id=" + id + ", customerId=" + customerId
				+ ", orderId=" + orderId + ", codeOfProduct=" + codeOfProduct
				+ ", quantity=" + quantity + ", price=" + price + "]";
	}
	
}
