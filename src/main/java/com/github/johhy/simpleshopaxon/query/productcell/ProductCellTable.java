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
public class ProductCellTable {

	@Id
	@GeneratedValue
	private Long id;
	
	private String codeOfProduct;
	
	private Integer quantity;
	
	private Double price;

	public ProductCellTable() {}
	
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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "ProductCellTable [id=" + id + ", codeOfProduct=" + codeOfProduct
				+ ", quantity=" + quantity + ", price=" + price + "]";
	}
}
