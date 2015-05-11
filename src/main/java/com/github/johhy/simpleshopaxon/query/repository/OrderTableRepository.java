package com.github.johhy.simpleshopaxon.query.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.github.johhy.simpleshopaxon.query.tables.OrderTable;

/**
 * The Interface OrderTableRepository.
 * <p>
 * 
 * @author johhy
 */
public interface OrderTableRepository 
	extends CrudRepository<OrderTable, Long> {

	/**
	 * Find by order id.
	 *
	 * @param orderId the order id
	 * @return the list
	 */
	List<OrderTable> findByOrderId(String orderId);
	
	/**
	 * Find by customer id.
	 *
	 * @param customerId the customer id
	 * @return the list
	 */
	List<OrderTable> findByCustomerId(String customerId);
	
	/**
	 * Find by order id and product id.
	 *
	 * @param orderId the order id
	 * @param productId the product id
	 * @return the order table
	 */
	OrderTable findByOrderIdAndProductId(String orderId, String productId);
	
	/**
	 * Find by customer id and product id.
	 *
	 * @param customerId the customer id
	 * @param productId the product id
	 * @return the order table
	 */
	OrderTable findByCustomerIdAndProductId(String customerId, 
		String productId);
}
