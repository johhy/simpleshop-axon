package com.github.johhy.simpleshopaxon.query.productcell;

import org.springframework.data.repository.CrudRepository;
/**
 * 
 * @author johhy
 *
 */
public interface ReservedProductRepository extends CrudRepository<ReservedProductTable, Long> {
	
	ReservedProductTable findByOrderIdAndCodeOfProduct(String orderId, String codeOfProduct);

}
