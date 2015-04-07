package com.github.johhy.simpleshopaxon.core.domain.customer;

import java.util.Map;

/**
 * The Interface CustomerLogica.
 * 
 * This is inner logic of customer.
 * After invoke method no change state of
 * variables, for save changed state use event
 * handlers.Return values also used for debug.
 * 
 * @author johhy
 */
public interface CustomerLogica {
	
	/**
	 * Sets the orders.
	 *
	 * @param orders the orders
	 */
	void setOrders(Map<String, Order> orders);
	
	/**
	 * Gets the orders.
	 *
	 * @return the orders
	 */
	Map<String, Order> getOrders();
	
	/**
	 * Checks if is any order exists.
	 *
	 * @return true, if is any order exists
	 */
	boolean isAnyOrderExists();
	
	/**
	 * Checks if is order exists.
	 *
	 * @param orderId the order id
	 * @return true, if is order exists
	 */
	boolean isOrderExists(String orderId);
	
	/**
	 * Find order.
	 *
	 * @param orderId the order id
	 * @return the order
	 */
	Order findOrder(String orderId);

	/**
	 * Creates the order.
	 *
	 * @param newOrderId the new order id
	 * @param logica the logica
	 * @return the order
	 */
	Order createOrder(String newOrderId, OrderLogica logica);
	
	/**
	 * Removes the order.
	 *
	 * @param orderIdToRemove the order id to remove
	 * @return the order
	 */
	Order removeOrder(String orderIdToRemove);

}
