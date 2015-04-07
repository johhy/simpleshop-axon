package com.github.johhy.simpleshopaxon.query.productcell;

import org.springframework.data.repository.CrudRepository;
/**
 * 
 * @author johhy
 *
 */
public interface ProductCellRepository extends CrudRepository<ProductCellTable, Long> {
	
	ProductCellTable findByCodeOfProduct(String codeOfProduct);
}
