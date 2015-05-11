package com.github.johhy.simpleshopaxon.web.rest;


import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.johhy.simpleshopaxon.facade.ApplicationException;
import com.github.johhy.simpleshopaxon.facade.FacadeCommandService;

/**
 * The Class ProductCellCommandController.
 * <p>
 * 
 * @author johhy
 */
@RestController
@RequestMapping(value = "/product", method = RequestMethod.POST)
public class ProductCellCommandController {

	/** The FacadeCommandService. */
	@Autowired
	private FacadeCommandService cs;
	
	/**
	 * Creates the product.
	 *
	 * @param productId the product id
	 * @param quantity the quantity
	 * @param price the price
	 * @param response the response
	 * @throws ApplicationException the application exception
	 */
	@RequestMapping("/{productId}/quantity/{quantity}/price/{price}")
	@ResponseStatus(value = HttpStatus.CREATED)
	public void createProduct(@PathVariable final String productId,
			@PathVariable final Integer quantity, 
			@PathVariable final Double price, 
			final HttpServletResponse response) 
					throws ApplicationException {
		cs.createProduct(productId, quantity, price);
		response.setHeader("Location", "/product/" + productId);
	}
	

	/**
	 * Adds the product.
	 *
	 * @param productId the product id
	 * @param quantity the quantity
	 * @throws ApplicationException the application exception
	 */
	@RequestMapping("/{productId}/quantity/{quantity}/add")
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public void addProduct(@PathVariable final String productId,
			@PathVariable final Integer quantity) 
					throws ApplicationException {
		cs.addAmountProduct(productId, quantity);
	}
	
	/**
	 * Removes the product.
	 *
	 * @param productId the product id
	 * @param quantity the quantity
	 * @throws ApplicationException the application exception
	 */
	@RequestMapping("/{productId}/quantity/{quantity}/remove")
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public void removeProduct(@PathVariable final String productId,
			@PathVariable final Integer quantity) 
					throws ApplicationException {
		cs.removeAmountProduct(productId, quantity);
	}
	
	/**
	 * Change capacity.
	 *
	 * @param productId the product id
	 * @param capacity the capacity
	 * @throws ApplicationException the application exception
	 */
	@RequestMapping("/{productId}/capacity/{capacity}")
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public void changeCapacity(@PathVariable final String productId,
			@PathVariable final Integer capacity) 
					throws ApplicationException {
		cs.changeCapacity(productId, capacity);
	}
	
	/**
	 * Change price.
	 *
	 * @param productId the product id
	 * @param price the price
	 * @throws ApplicationException the application exception
	 */
	@RequestMapping("/{productId}/price/{price}")
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public void changePrice(@PathVariable final String productId,
			@PathVariable final Double price) 
					throws ApplicationException {
		cs.changeProductPrice(productId, price);
	}
	

}
