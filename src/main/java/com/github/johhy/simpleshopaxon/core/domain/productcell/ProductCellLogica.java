package com.github.johhy.simpleshopaxon.core.domain.productcell;

import java.util.Map;

/**
 * The Interface ProductCellLogica.
 * 
 * This is inner logic of product cell.
 * After invoke method no change state of
 * variables, for save changed state use event
 * handlers.Return values also used for debug.
 * 
 * @author johhy
 */
public interface ProductCellLogica {

	/**
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	int getQuantity();
	
	/**
	 * Sets the quantity.
	 *
	 * @param quantity the new quantity
	 */
	void setQuantity(int quantity); 
	
	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	double getPrice();
	
	/**
	 * Sets the price.
	 *
	 * @param price the new price
	 */
	void setPrice(double price);
	
	/**
	 * Gets the reserved products.
	 *
	 * @return the reserved products
	 */
	Map<String, Integer> getReservedProducts();
	
	/**
	 * Sets the reserved products.
	 *
	 * @param reservedProducts the reserved products
	 */
	void setReservedProducts(Map<String, Integer> reservedProducts);
	 
	/**
	 * Checks if is product more than need.
	 *
	 * @param amountReqired the amount reqired
	 * @return true, if is product more than need
	 */
	boolean isProductMoreThanNeed(int amountReqired);
	 
	 /**
 	 * Removes the amount product from product cell.
 	 *
 	 * @param removedAmount the removed amount
 	 * @return the int
 	 */
 	int removeAmountProductFromProductCell(int removedAmount);
	 
	 /**
 	 * Adds the amount product to product cell.
 	 *
 	 * @param addedAmount the added amount
 	 * @return the int
 	 */
 	int addAmountProductToProductCell(int addedAmount);
	 
	 /**
 	 * Change price.
 	 *
 	 * @param newPrice the new price
 	 * @return the double
 	 */
 	double changePrice(double newPrice);
	 
	 /**
 	 * Checks if is product reserved for order.
 	 *
 	 * @param orderId the order id
 	 * @return true, if is product reserved for order
 	 */
 	boolean isProductReservedForOrder(String orderId);
	 
	 /**
 	 * Checks if is amount reserved product more than need to remove.
 	 *
 	 * @param orderId the order id
 	 * @param amountReqired the amount reqired
 	 * @return true, if is amount reserved product more than need to remove
 	 */
 	boolean isAmountReservedProductMoreThanNeedToRemove(String orderId, int amountReqired);
	 
	 /**
 	 * Removes the amount reserved product for order.
 	 *
 	 * @param orderId the order id
 	 * @param amountToRemove the amount to remove
 	 * @return the int
 	 */
 	int removeAmountReservedProductForOrder(String orderId, int amountToRemove);
	
	 /**
 	 * Adds the reserved product for order.
 	 *
 	 * @param orderId the order id
 	 * @param amountToAdd the amount to add
 	 * @return the int
 	 */
 	int addReservedProductForOrder(String orderId, int amountToAdd);
	 
	 /**
 	 * Reserve product for order.
 	 *
 	 * @param orderId the order id
 	 * @param amountToReserve the amount to reserve
 	 */
 	void reserveProductForOrder(String orderId, int amountToReserve);
	 
	 /**
 	 * Checks if is any reserved product in product cell.
 	 *
 	 * @return true, if is any reserved product in product cell
 	 */
 	boolean isAnyReservedProductInProductCell();
}
