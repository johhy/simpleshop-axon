package com.github.johhy.simpleshopaxon.core.events.productcell;

/**
 * 
 * @author johhy
 *
 */
public class ProductCellRemovedEvent {
	
	private final String codeOfProduct;

	public ProductCellRemovedEvent(String codeOfProduct) {
		super();
		this.codeOfProduct = codeOfProduct;
	}

	public String getCodeOfProduct() {
		return codeOfProduct;
	}

	@Override
	public String toString() {
		return "ProductCellRemovedEvent [codeOfProduct=" + codeOfProduct + "]";
	}
	
}
