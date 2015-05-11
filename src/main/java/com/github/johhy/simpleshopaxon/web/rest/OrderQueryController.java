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

import com.github.johhy.simpleshopaxon.query.repository.OrderTableRepository;
import com.github.johhy.simpleshopaxon.query.tables.OrderTable;
import com.github.johhy.simpleshopaxon.web.rest.dto.Order;

/**
 * The Class OrderQueryController.
 * <p>
 * 
 * @author johhy
 */
@RestController
@RequestMapping(value = "/order", method = RequestMethod.GET)
public class OrderQueryController {

	/** The ot repo. */
	@Autowired
	private OrderTableRepository otRepo;
	
	/**
	 * Gets the all orders.
	 *
	 * @return the all orders
	 */
	@RequestMapping("/")
	@ResponseStatus(value = HttpStatus.OK)
	public List<Order> getAllOrders() {
		return converListToDtoList((List<OrderTable>) otRepo.findAll());
	}
	
	/**
	 * Gets the all orders for customer.
	 *
	 * @param customerId the customer id
	 * @return the all orders for customer
	 */
	@RequestMapping("{/customer/customerId}")
	@ResponseStatus(value = HttpStatus.OK)
	public List<Order> getAllOrdersForCustomer(
			@PathVariable final String customerId) {
		return converListToDtoList(otRepo.findByCustomerId(customerId));
	}
	
	/**
	 * Gets the all orders for order id.
	 *
	 * @param orderId the order id
	 * @return the all orders for order id
	 */
	@RequestMapping("/{orderId}")
	@ResponseStatus(value = HttpStatus.OK)
	public List<Order> getAllOrdersForOrderId(
			@PathVariable final String orderId) {
		return converListToDtoList(otRepo.findByOrderId(orderId));
	}
	
	/**
	 * Gets the order for customer and product.
	 *
	 * @param customerId the customer id
	 * @param productId the product id
	 * @return the order for customer and product
	 */
	@RequestMapping("/customer/{customerId}/product/{productId}")
	@ResponseStatus(value = HttpStatus.OK)
	public Order getOrderForCustomerAndProduct(
			@PathVariable final String customerId,
			@PathVariable final String productId) {
		return convertToDto(otRepo
				.findByCustomerIdAndProductId(customerId, productId));
	}
	
	/**
	 * Gets the order for product.
	 *
	 * @param orderId the order id
	 * @param productId the product id
	 * @return the order for product
	 */
	@RequestMapping("/{orderId}/product/{productId}")
	@ResponseStatus(value = HttpStatus.OK)
	public Order getOrderForProduct(
			@PathVariable final String orderId,
			@PathVariable final String productId) {
		return convertToDto(otRepo
				.findByOrderIdAndProductId(orderId, productId));
	}
	
	/**
	 * Conver list to dto list.
	 *
	 * @param list the list
	 * @return the list
	 */
	private List<Order> converListToDtoList(final List<OrderTable> list) {
		List<Order> res = new ArrayList<Order>();
		for (OrderTable table:list) {
			res.add(new Order(
					table.getOrderId(),
					table.getCustomerId(),
					table.getShipTo(),
					table.getOrderSatus(),
					table.getTotal(),
					table.getCreated(),
					table.getProductId(),
					table.getQuantity(),
					table.getPrice()));
		}
		return res;
	}
	
	/**
	 * Convert to dto.
	 *
	 * @param table the table
	 * @return the order
	 */
	private Order convertToDto(final OrderTable table) {
		if (table != null) {
			return new Order(
					table.getOrderId(),
					table.getCustomerId(),
					table.getShipTo(),
					table.getOrderSatus(),
					table.getTotal(),
					table.getCreated(),
					table.getProductId(),
					table.getQuantity(),
					table.getPrice());
		} else {
		    return null;
		}
	}
}
