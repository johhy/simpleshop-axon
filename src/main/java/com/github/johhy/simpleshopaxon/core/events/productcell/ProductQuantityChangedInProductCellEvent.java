package com.github.johhy.simpleshopaxon.core.events.productcell;

/**
 * @author johhy
 *
 */
public class ProductQuantityChangedInProductCellEvent {
	
	private final String codeOfProduct;
	private final int quantity;

	public  ProductQuantityChangedInProductCellEvent(String codeOfProduct, int quantity) {
		super();
		this.codeOfProduct = codeOfProduct;
		this.quantity = quantity;
	}

	public String getCodeOfProduct() {
		return codeOfProduct;
	}

	public int getQuantity() {
		return quantity;
	}

	@Override
	public String toString() {
		return "ProductQuantityChangedInProductCellEvent [codeOfProduct="
				+ codeOfProduct + ", quantity=" + quantity + "]";
	}
	
}
