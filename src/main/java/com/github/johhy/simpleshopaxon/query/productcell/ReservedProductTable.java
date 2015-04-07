package com.github.johhy.simpleshopaxon.query.productcell;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 
 * @author johhy
 *
 */
@Entity
public class ReservedProductTable {

	@Id
	@GeneratedValue
	private Long id;
	
	private String codeOfProduct;
	
	private String orderId;
	
	private int amount;
	
	public ReservedProductTable() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodeOfProduct() {
		return codeOfProduct;
	}

	public void setCodeOfProduct(String codeOfProduct) {
		this.codeOfProduct = codeOfProduct;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "ReservedProductTable [id=" + id + ", codeOfProduct="
				+ codeOfProduct + ", orderId=" + orderId + ", amount=" + amount
				+ "]";
	}
	
}
