package com.github.johhy.simpleshopaxon.core.domain.customer;

import java.util.HashMap;
import java.util.Map;

import org.axonframework.domain.DomainEventMessage;
import org.axonframework.eventsourcing.GenericAggregateFactory;

/**
 * A factory for creating CustomerAggregate objects.
 * 
 * @author johhy
 */
public class CustomerAggregateFactory extends GenericAggregateFactory<Customer> {

	/** The customer logica class. */
	private Class<?> customerLogicaClass;
	
	/** The order logica class. */
	private Class<?> orderLogicaClass;
	
	/**
	 * Instantiates a new customer aggregate factory.
	 *
	 * @param customerLogica the customer logica class
	 * @param orderLogica the order logica class
	 */
	public CustomerAggregateFactory(Class<?> customerLogica, Class<?> orderLogica) {
		super(Customer.class);
		this.customerLogicaClass = customerLogica;
		this.orderLogicaClass = orderLogica;
	}
	
	/* (non-Javadoc)
	 * @see org.axonframework.eventsourcing.GenericAggregateFactory#doCreateAggregate(java.lang.Object, org.axonframework.domain.DomainEventMessage)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected Customer doCreateAggregate(Object aggregateIdentifier, DomainEventMessage firstEvent) {
		CustomerLogica customerLogica = null;
		OrderLogica orderLogica = null;
		try {
			//create new instance of customer logica
			customerLogica = (CustomerLogica) customerLogicaClass.newInstance();
			//create new instance of order logica
			orderLogica = (OrderLogica) orderLogicaClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		Map<String, Order> orders = new HashMap<String,Order>();
		Customer customer = new Customer(customerLogica, orderLogica);
		customer.setOrders(orders);
		return customer;
	}

}
