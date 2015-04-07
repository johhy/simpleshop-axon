package com.github.johhy.simpleshopaxon.core.domain.customer;

import java.util.Map;

import com.jcabi.aspects.Loggable;

/**
 * The Class CustomerLogicaImpl.
 * Impl Customer logica injected in aggregate.
 * 
 * @author johhy
 */
public class CustomerLogicaImpl implements CustomerLogica {


	/** The orders. */
	private Map<String, Order> orders;

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.domain.customer.CustomerLogica#setOrders(java.util.Map)
	 */
	public void setOrders(Map<String, Order> orders) {
		this.orders = orders;
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.domain.customer.CustomerLogica#getOrders()
	 */
	public Map<String, Order> getOrders() {
		return orders;
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.domain.customer.CustomerLogica#isAnyOrderExists()
	 */
	@Loggable(Loggable.DEBUG)
	public boolean isAnyOrderExists() {
		return !orders.isEmpty();
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.domain.customer.CustomerLogica#isOrderExists(java.lang.String)
	 */
	@Loggable(Loggable.DEBUG)
	public boolean isOrderExists(String orderId) {
		return orders.containsKey(orderId);
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.domain.customer.CustomerLogica#findOrder(java.lang.String)
	 */
	@Loggable(Loggable.DEBUG)
	public Order findOrder(String orderId) {
		return orders.get(orderId);
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.domain.customer.CustomerLogica#createOrder(java.lang.String, com.github.johhy.simpleshopaxon.core.domain.customer.OrderLogica)
	 */
	@Loggable(Loggable.DEBUG)
	public Order createOrder(String newOrderId, OrderLogica logica) {
		Order newOrder = new Order(logica);
		newOrder.setOrderId(newOrderId);
		orders.put(newOrderId, newOrder);
		return newOrder;
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.domain.customer.CustomerLogica#removeOrder(java.lang.String)
	 */
	@Loggable(Loggable.DEBUG)
	public Order removeOrder(String orderIdToRemove) {
		return orders.remove(orderIdToRemove);
	}

}
