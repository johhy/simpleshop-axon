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
 * The Class OrderCommandController.
 * <p>
 * 
 * @author johhy
 */
@RestController
@RequestMapping(value = "/order", method = RequestMethod.POST)
public class OrderCommandController {
	
	/** The cs. */
	@Autowired
	private FacadeCommandService cs;
	
	/**
	 * Creates the order.
	 *
	 * @param customerId the customer id
	 * @param shipTo the ship to
	 * @param response the response
	 * @throws ApplicationException the application exception
	 */
	@RequestMapping("/customer/{customerId}/ship/{shipTo}")
	@ResponseStatus(value = HttpStatus.CREATED)
	public void createOrder(@PathVariable final String customerId, 
			@PathVariable final String shipTo,
			final HttpServletResponse response) throws ApplicationException {
		String orderId = cs
			.createOrderFromShoppingCartForCustomer(customerId, shipTo);
		response.setHeader("Location", "/order/" + orderId);
	}
	
	/**
	 * Change order status.
	 *
	 * @param orderId the order id
	 * @param statusCode the status code
	 * @throws ApplicationException the application exception
	 */
	@RequestMapping("/{orderId}/status/{statusCode}")
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public void changeOrderStatus(@PathVariable final String orderId, 
			@PathVariable final Integer statusCode) 
			throws ApplicationException {
		cs.changeOrderStatus(orderId, statusCode);
	}

}
