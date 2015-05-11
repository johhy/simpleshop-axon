package com.github.johhy.simpleshopaxon.facade;


/**
 * The Interface FacadeCommandService.
 * <p>
 * Main facade for clients.
 * Converts from API client to API core domains.
 * Client invoke method - service generate and send commands to
 * command part of core.
 * If exceptions throws it return for client and no any result
 * are returned for client. 
 * 
 * @author johhy
 */
public interface FacadeCommandService {

	/**
	 * Creates the product.
	 *
	 * @param productId the product id
	 * @param quantity the quantity
	 * @param price the price
	 * @throws ApplicationException the application exception
	 */
	void createProduct(String productId, Integer quantity, Double price) throws
		ApplicationException;
	
	/**
	 * Adds the amount product.
	 *
	 * @param productId the product id
	 * @param amountToAdd the amount to add
	 * @throws ApplicationException the application exception
	 */
	void addAmountProduct(String productId, Integer amountToAdd) throws
		ApplicationException;
	
	/**
	 * Removes the amount product.
	 *
	 * @param productId the product id
	 * @param amountToRemove the amount to remove
	 * @throws ApplicationException the application exception
	 */
	void removeAmountProduct(String productId, Integer amountToRemove) throws
		ApplicationException;
	
	/**
	 * Change capacity.
	 *
	 * @param productId the product id
	 * @param newCapacity the new capacity
	 * @throws ApplicationException the application exception
	 */
	void changeCapacity(String productId, Integer newCapacity) throws
		ApplicationException;
	
	/**
	 * Adds the product to customer shopping cart.
	 *
	 * @param productId the product id
	 * @param amountToGive the amount to give
	 * @param customerId the customer id
	 * @throws ApplicationException the application exception
	 */
	void addProductToCustomerShoppingCart(String productId, 
		Integer amountToGive, String customerId) throws ApplicationException;
	
	/**
	 * Removes the product from customer shopping cart.
	 *
	 * @param productId the product id
	 * @param amountToRemove the amount to remove
	 * @param customerId the customer id
	 * @throws ApplicationException the application exception
	 */
	void removeProductFromCustomerShoppingCart(String productId, 
		Integer amountToRemove, String customerId) throws ApplicationException;
	
	/**
	 * Change product price.
	 *
	 * @param productId the product id
	 * @param newPrice the new price
	 * @throws ApplicationException the application exception
	 */
	void changeProductPrice(String productId, Double newPrice) throws
		ApplicationException;
	
	/**
	 * Creates the customer.
	 *
	 * @param address the address
	 * @return the string
	 * @throws ApplicationException the application exception
	 */
	String createCustomer(String address) throws
		ApplicationException;
	
	/**
	 * Change customer address.
	 *
	 * @param customerId the customer id
	 * @param newAddress the new address
	 * @throws ApplicationException the application exception
	 */
	void changeCustomerAddress(String customerId, String newAddress) throws
		ApplicationException;
	
	/**
	 * Creates the order from shopping cart for customer.
	 *
	 * @param customerId the customer id
	 * @param shipTo the ship to
	 * @return the string
	 * @throws ApplicationException the application exception
	 */
	String createOrderFromShoppingCartForCustomer(String customerId, 
		String shipTo) throws ApplicationException;
	
	/**
	 * Change order status.
	 *
	 * @param orderId the order id
	 * @param statusCode the status code
	 * @throws ApplicationException the application exception
	 */
	void changeOrderStatus(String orderId, Integer statusCode) throws
		ApplicationException;
	
}
