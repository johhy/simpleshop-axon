package com.github.johhy.simpleshopaxon.core.domain.customer;

import java.util.HashMap;
import java.util.Map;

import com.jcabi.aspects.Loggable;

/**
 * The Class OrderLogicaImpl.
 * 
 * Order logica injected in aggregate
 * 
 * @author johhy
 */
public class OrderLogicaImpl implements OrderLogica {


	/** The products. */
	Map<String, Product> products = new HashMap<String, Product>();

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.domain.customer.OrderLogica#getProducts()
	 */
	public Map<String, Product> getProducts() {
		return products;
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.domain.customer.OrderLogica#setProducts(java.util.Map)
	 */
	public void setProducts(Map<String, Product> products) {
		this.products = products;
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.domain.customer.OrderLogica#isOrderEmpty()
	 */
	@Loggable(Loggable.DEBUG)
	public boolean isOrderEmpty() {
		return products.isEmpty();
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.domain.customer.OrderLogica#isProductExists(java.lang.String)
	 */
	@Loggable(Loggable.DEBUG)
	public boolean isProductExists(String codeOfProduct) {
		return products.containsKey(codeOfProduct);
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.domain.customer.OrderLogica#createProductInOrder(java.lang.String, int, double)
	 */
	@Loggable(Loggable.DEBUG)
	public Product createProductInOrder(String codeOfProduct, int quantity,
			double price) {
		Product productToAdd = new Product(codeOfProduct, quantity, 
				price);
		products.put(codeOfProduct, productToAdd);
		return productToAdd;
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.domain.customer.OrderLogica#removeProductFromOrder(java.lang.String)
	 */
	@Loggable(Loggable.DEBUG)
	public Product removeProductFromOrder(String codeOfProduct) {
		return products.remove(codeOfProduct);
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.domain.customer.OrderLogica#isAmountProductToRemoveEqualInOrder(java.lang.String, int)
	 */
	public boolean isAmountProductToRemoveEqualInOrder(String codeOfProduct,
			int amountToRemove) {
		return amountToRemove==products.get(codeOfProduct).getQuantity();
	}	

}
