package com.github.johhy.simpleshopaxon.query.productcell;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
/**
 * 
 * @author johhy
 *
 */
public interface ReservedProductRepository extends CrudRepository<ReservedProductTable, Long> {
	
	List<ReservedProductTable> findByCodeOfProduct(String codeOfProduct);
	
	ReservedProductTable findByOrderIdAndCodeOfProduct(String orderId, String codeOfProduct);

}
