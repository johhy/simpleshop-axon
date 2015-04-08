package com.github.johhy.simpleshopaxon.core.domain.customer;

/**
 * @author johhy
 * <p>
 * Domain object. Value Object.
 * <p>
 * Product in order as value object.
 * If needs to change price or quantity, old product should be removed
 * from order and new created product add with new values.
 * 
 */
public class Product {
	
	/** The quantity. */
	private final int quantity;
	
	private final double price;
	
	public Product(int quantity, double price) {
		super();
		this.quantity = quantity;
		this.price = price;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public double getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "Product [quantity=" + quantity + ", price=" + price + "]";
	}


}
