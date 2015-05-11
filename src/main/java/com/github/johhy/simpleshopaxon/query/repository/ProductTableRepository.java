package com.github.johhy.simpleshopaxon.query.repository;

import org.springframework.data.repository.CrudRepository;

import com.github.johhy.simpleshopaxon.query.tables.ProductTable;

/**
 * The Interface ProductTableRepository.
 * <p>
 * 
 * @author johhy
 */
public interface ProductTableRepository 
	extends CrudRepository<ProductTable, Long> {

	/**
	 * Find by product id.
	 *
	 * @param productId the product id
	 * @return the product table
	 */
	ProductTable findByProductId(String productId);
}
