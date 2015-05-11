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
 * The Class CustomerCommandController.
 * <p>
 * 
 * @author johhy
 */
@RestController
@RequestMapping(value = "/customer", method = RequestMethod.POST)
public class CustomerCommandController {

	/** The cs. */
	@Autowired
	private FacadeCommandService cs;
	
	/**
	 * Creates the customer.
	 *
	 * @param address the address
	 * @param response the response
	 * @throws ApplicationException the application exception
	 */
	@RequestMapping("/{address}")
	@ResponseStatus(value = HttpStatus.CREATED)
	public void createCustomer(@PathVariable final String address,
			final HttpServletResponse response) throws ApplicationException {
		String customerId = cs.createCustomer(address);
		response.setHeader("Location", "/customer/" + customerId);
	}
	
	/**
	 * Change customer address.
	 *
	 * @param customerId the customer id
	 * @param address the address
	 * @throws ApplicationException the application exception
	 */
	@RequestMapping("/{customerId}/address/{address}")
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public void changeCustomerAddress(@PathVariable final String customerId, 
			@PathVariable final String address) throws ApplicationException {
		cs.changeCustomerAddress(customerId, address);
	}
	
	/**
	 * Adds the product for customer.
	 *
	 * @param customerId the customer id
	 * @param productId the product id
	 * @param quantity the quantity
	 * @throws ApplicationException the application exception
	 */
	@RequestMapping("/{customerId}/product/{productId}/quantity/{quantity}/add")
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public void addProductForCustomer(@PathVariable final String customerId, 
			@PathVariable final String productId, 
			@PathVariable final Integer quantity) 
					throws ApplicationException {
		cs.addProductToCustomerShoppingCart(productId, quantity, customerId);
	}
	
	/**
	 * Removes the product from customer.
	 *
	 * @param customerId the customer id
	 * @param productId the product id
	 * @param quantity the quantity
	 * @throws ApplicationException the application exception
	 */
	@RequestMapping("/{customerId}/product/{productId}/quantity/{quantity}/"
		+ "remove")
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public void removeProductFromCustomer(@PathVariable final String customerId,
			@PathVariable final String productId, 
			@PathVariable final Integer quantity) 
					throws ApplicationException {
		cs.removeProductFromCustomerShoppingCart(productId, quantity, 
			customerId);
	}
	
}
