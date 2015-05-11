package com.github.johhy.simpleshopaxon.query.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.github.johhy.simpleshopaxon.query.tables.CustomerTable;

/**
 * The Interface CustomerTableRepository.
 * <p>
 * 
 * @author johhy
 */
public interface CustomerTableRepository 
	extends CrudRepository<CustomerTable, Long> {

	/**
	 * Find by customer id.
	 *
	 * @param customerId the customer id
	 * @return the list
	 */
	List<CustomerTable> findByCustomerId(String customerId);
	
	/**
	 * Find by customer id and product id.
	 *
	 * @param customerId the customer id
	 * @param productId the product id
	 * @return the customer table
	 */
	CustomerTable findByCustomerIdAndProductId(String customerId, 
		String productId);
}
