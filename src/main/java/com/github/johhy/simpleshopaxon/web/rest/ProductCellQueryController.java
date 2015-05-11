package com.github.johhy.simpleshopaxon.web.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.johhy.simpleshopaxon.query.repository.ProductTableRepository;
import com.github.johhy.simpleshopaxon.query.tables.ProductTable;
import com.github.johhy.simpleshopaxon.web.rest.dto.Product;

/**
 * The Class ProductCellQueryController.
 * <p>
 * 
 * @author johhy
 */
@RestController
@RequestMapping(value = "/productcell", method = RequestMethod.GET)
public class ProductCellQueryController {

	/** The ProductTableRepository repo. */
	@Autowired
	private ProductTableRepository ptRepo;
	
	/**
	 * Gets the all products.
	 *
	 * @return the all products
	 */
	@RequestMapping("/")
	@ResponseStatus(value = HttpStatus.OK)
	public List<Product> getAllProducts() {
		return convertListToListDto((List<ProductTable>) ptRepo.findAll());
	}
	
	/**
	 * Gets the product.
	 *
	 * @param productId the product id
	 * @return the product
	 */
	@RequestMapping("/{productId}")
	@ResponseStatus(value = HttpStatus.OK)
	public Product getProduct(@PathVariable final String productId) {
		return convertToDto(ptRepo.findByProductId(productId));
	}
	
	/**
	 * Convert list to list dto.
	 *
	 * @param list the list
	 * @return the list
	 */
	private List<Product> convertListToListDto(final List<ProductTable> list) {
		List<Product> res = new ArrayList<Product>();
		for (ProductTable table:list) {
			res.add(new Product(table.getProductId(),
					table.getQuantity(), table.getPrice()));
		}
		return res;
	}
	
	/**
	 * Convert to dto.
	 *
	 * @param table the table
	 * @return the product
	 */
	private Product convertToDto(final ProductTable table) {
		if (table != null) {
			return new Product(table.getProductId(), 
					table.getQuantity(), table.getPrice());
		} else {
		    return null;
		}
	}
}
