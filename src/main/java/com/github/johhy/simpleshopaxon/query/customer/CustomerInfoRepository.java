package com.github.johhy.simpleshopaxon.query.customer;

import org.springframework.data.repository.CrudRepository;

/**
 * @author johhy
 *
 */
public interface CustomerInfoRepository extends CrudRepository<CustomerInfoTable, Long> {

	CustomerInfoTable findByCustomerId(String customerId);
}
