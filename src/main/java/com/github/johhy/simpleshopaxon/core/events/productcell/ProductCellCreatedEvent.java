package com.github.johhy.simpleshopaxon.core.events.productcell;

/**
 * @author johhy
 *
 */
public class ProductCellCreatedEvent {

	private final String codeOfProduct;
	
	public ProductCellCreatedEvent(String codeOfProduct) {
		super();
		this.codeOfProduct = codeOfProduct;
	}

	public String getCodeOfProduct() {
		return codeOfProduct;
	}

	@Override
	public String toString() {
		return "ProductCellCreatedEvent [codeOfProduct=" + codeOfProduct + "]";
	}

	
}
