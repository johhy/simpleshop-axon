package com.github.johhy.simpleshopaxon.query.customer;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * @author johhy
 *
 */
public interface CustomerRepository extends CrudRepository<CustomerTable, Long> {
	
	CustomerTable findByCustomerIdAndOrderIdAndCodeOfProduct(String customerId,
			String orderId, String codeOfProduct);
	
	List<CustomerTable> findByCustomerIdAndOrderId(String customerId, String orderId);
	
	List<CustomerTable> findByCustomerId(String customerId);
}
