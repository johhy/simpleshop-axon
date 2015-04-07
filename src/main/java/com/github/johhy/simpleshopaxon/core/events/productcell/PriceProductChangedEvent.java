package com.github.johhy.simpleshopaxon.core.events.productcell;

/**
 * @author johhy
 *
 */
public class PriceProductChangedEvent {
	
	private final String codeOfProduct;
	private final double price;
	
	public PriceProductChangedEvent(String codeOfProduct, double price) {
		super();
		this.codeOfProduct = codeOfProduct;
		this.price = price;
	}

	public String getCodeOfProduct() {
		return codeOfProduct;
	}

	public double getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "PriceProductChangedEvent [codeOfProduct=" + codeOfProduct
				+ ", price=" + price + "]";
	}
	
}
