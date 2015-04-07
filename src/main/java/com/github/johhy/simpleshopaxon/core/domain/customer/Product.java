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
	
	/** The code of product. */
	private final String codeOfProduct;
	
	/** The quantity. */
	private final int quantity;
	
	private final double price;
	
	public Product(String codeOfProduct, int quantity, double price) {
		super();
		this.codeOfProduct = codeOfProduct;
		this.quantity = quantity;
		this.price = price;
	}
	
	public String getCodeOfProduct() {
		return codeOfProduct;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public double getPrice() {
		return price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeOfProduct == null) ? 0 : codeOfProduct.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (codeOfProduct == null) {
			if (other.codeOfProduct != null)
				return false;
		} else if (!codeOfProduct.equals(other.codeOfProduct))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [codeOfProduct=" + codeOfProduct + ", quantity="
				+ quantity + ", price=" + price + "]";
	}
	
	
}
