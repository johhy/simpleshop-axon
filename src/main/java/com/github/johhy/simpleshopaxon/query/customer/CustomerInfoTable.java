package com.github.johhy.simpleshopaxon.query.customer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author johhy
 *
 */
@Entity
public class CustomerInfoTable {

	@Id
	@GeneratedValue
	private Long id;
	
	private String customerId;
	
	private String customerName;
	
	private String customerLogin;
	
	public CustomerInfoTable() {}

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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerLogin() {
		return customerLogin;
	}

	public void setCustomerLogin(String customerLogin) {
		this.customerLogin = customerLogin;
	}

	@Override
	public String toString() {
		return "CustomerInfoTable [id=" + id + ", customerId=" + customerId
				+ ", customerName=" + customerName + ", customerLogin="
				+ customerLogin + "]";
	}
	
}
