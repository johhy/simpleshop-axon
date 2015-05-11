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

import com.github.johhy.simpleshopaxon.query.repository.CustomerTableRepository;
import com.github.johhy.simpleshopaxon.query.tables.CustomerTable;
import com.github.johhy.simpleshopaxon.web.rest.dto.Customer;

/**
 * The Class CustomerQueryController.
 * <p>
 * 
 * @author johhy
 */
@RestController
@RequestMapping(value = "/customer", method = RequestMethod.GET)
public class CustomerQueryController {

	/** The ct repo. */
	@Autowired
	private CustomerTableRepository ctRepo;
	
	/**
	 * Gets the all customer.
	 *
	 * @return the all customer
	 */
	@RequestMapping("/")
	@ResponseStatus(value = HttpStatus.OK)
	public List<Customer> getAllCustomer() {
		return converterListToDtoList((List<CustomerTable>) ctRepo
				.findAll());
	}
	
	/**
	 * Gets the all products for customer.
	 *
	 * @param customerId the customer id
	 * @return the all products for customer
	 */
	@RequestMapping("/{customerId}")
	@ResponseStatus(value = HttpStatus.OK)
	public List<Customer> getAllProductsForCustomer(
			@PathVariable final String customerId) {
		return converterListToDtoList((List<CustomerTable>) ctRepo
				.findByCustomerId(customerId));
	}
	
	/**
	 * Gets the product for customer.
	 *
	 * @param customerId the customer id
	 * @param productId the product id
	 * @return the product for customer
	 */
	@RequestMapping("/{customerId}/product/{productId}")
	@ResponseStatus(value = HttpStatus.OK)
	public Customer getProductForCustomer(
			@PathVariable final String customerId,
			@PathVariable final String productId) {
		return convertToDto(ctRepo
				.findByCustomerIdAndProductId(customerId, productId));
	}
	
	/**
	 * Converter list to dto list.
	 *
	 * @param list the list
	 * @return the list
	 */
	private List<Customer> 
	converterListToDtoList(final List<CustomerTable> list) {
		List<Customer> result = new ArrayList<Customer>();
		for (CustomerTable table:list) {
			result.add(new Customer(
					table.getCustomerId(), 
					table.getAddress(),
					table.getCreated(),
					table.getProductId(), 
					table.getQuantity(),
					table.getPrice()));
		}
		return result;
	}
	
	/**
	 * Convert to dto.
	 *
	 * @param table the table
	 * @return the customer
	 */
	private Customer convertToDto(final CustomerTable table) {
		if (table != null) {
			return new Customer(
					table.getCustomerId(),
					table.getAddress(),
					table.getCreated(),
					table.getProductId(),
					table.getQuantity(),
					table.getPrice());
		} else {
		    return null;
		}
	}
}
