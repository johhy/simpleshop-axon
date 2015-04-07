package com.github.johhy.simpleshopaxon.core.domain.customer;

import java.util.Map;

/**
 * The Interface OrderLogica.
 * 
 * This is inner logic of order.
 * After invoke method no change state of
 * variables, for save changed state use event
 * handlers.Return values also used for debug.
 * 
 * @author johhy
 */
public interface OrderLogica {
	
	/**
	 * Gets the products.
	 *
	 * @return the products
	 */
	Map<String, Product> getProducts();
	
	/**
	 * Sets the products.
	 *
	 * @param products the products
	 */
	void setProducts(Map<String, Product> products);
	
	/**
	 * Checks if is order empty.
	 *
	 * @return true, if is order empty
	 */
	boolean isOrderEmpty(); 
	
	/**
	 * Checks if is product exists.
	 *
	 * @param codeOfProduct the code of product
	 * @return true, if is product exists
	 */
	boolean isProductExists(String codeOfProduct);
	
	/**
	 * Creates the product in order.
	 *
	 * @param codeOfProduct the code of product
	 * @param quantity the quantity
	 * @param price the price
	 * @return the product
	 */
	Product createProductInOrder(String codeOfProduct, 
			int quantity, double price);

	/**
	 * Removes the product from order.
	 *
	 * @param codeOfProduct the code of product
	 * @return the product
	 */
	Product removeProductFromOrder(String codeOfProduct);
	
	/**
	 * Checks if is amount product to remove equal in order.
	 *
	 * @param codeOfProduct the code of product
	 * @param amountToRemove the amount to remove
	 * @return true, if is amount product to remove equal in order
	 */
	boolean isAmountProductToRemoveEqualInOrder(String codeOfProduct, 
			int amountToRemove);

}
