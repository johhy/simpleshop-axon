package com.github.johhy.simpleshopaxon.core.domain.customer;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Transient;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.commandhandling.annotation.CommandHandlingMember;
import org.axonframework.commandhandling.annotation.CommandHandlingMemberMap;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcedMember;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import com.github.johhy.simpleshopaxon.core.commands.customer.CreateCustomerCommand;
import com.github.johhy.simpleshopaxon.core.commands.customer.CreateOrderCommand;
import com.github.johhy.simpleshopaxon.core.commands.customer.DeleteOrderCommand;
import com.github.johhy.simpleshopaxon.core.commands.customer.RemoveCustomerCommand;
import com.github.johhy.simpleshopaxon.core.events.customer.CustomerCreatedEvent;
import com.github.johhy.simpleshopaxon.core.events.customer.CustomerRemovedEvent;
import com.github.johhy.simpleshopaxon.core.events.customer.OrderCreatedEvent;
import com.github.johhy.simpleshopaxon.core.events.customer.OrderDeletedEvent;
import com.github.johhy.simpleshopaxon.core.exceptions.customer.OrderNotEmptyException;
import com.github.johhy.simpleshopaxon.core.exceptions.customer.OrderNotFoundException;
import com.jcabi.aspects.Loggable;

/**
 * Domain object.Aggregate Root.
 * * <p>
* Domain command and event handler
* <p>
* Methods for commands and events handle for domain
* no have domain logic. Used only for validation and
* generate events or exceptions.How it work see
* <a href="http://www.axonframework.org/">axonframework </a>
* documentation.
* Returned values in methods use for debug only,
* apply used for save and publish.
* 
 * @author johhy
 *
 */
public class Customer extends AbstractAnnotatedAggregateRoot<String> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The logica of aggregate. */
	@Transient
	private CustomerLogica logica;
	
	/** The order logica. */
	@Transient
	private OrderLogica orderLogica;
	
	/** The customer id. */
	@AggregateIdentifier
	private String customerId;
	

	/** The orders. 
	 * orderId is key in Map */
	@CommandHandlingMemberMap(commandTargetProperty="orderId")
	@EventSourcedMember
	private Map<String, Order> orders = new HashMap<String, Order>(); 
	
	/** The customer info. */
	@CommandHandlingMember
	@EventSourcedMember
	private CustomerInfo customerInfo;
	
	/**
	 * Instantiates a new customer.
	 */
	public Customer() {};
	
	/**
	 * Instantiates a new customer.
	 *
	 * @param logica the logica
	 * @param orderLogica the order logica
	 */
	public Customer(CustomerLogica logica, OrderLogica orderLogica) {
		this.logica = logica;
		this.orderLogica = orderLogica;
	}
	
	/**
	 * Instantiates a new customer.
	 *
	 * @param command the command
	 */
	@CommandHandler
	public Customer(CreateCustomerCommand command) {
		apply(new CustomerCreatedEvent(command.getCustomerId(),
				command.getCustomerName(),command.getCustomerLogin()));
	}
	
	/**
	 * Created customer event.
	 *
	 * @param event the event
	 */
	@EventSourcingHandler
	public void createdCustomerEvent(CustomerCreatedEvent event) {
		this.customerId = event.getCustomerId();
		this.customerInfo = new CustomerInfo(event.getCustomerName(), 
				event.getCustomerLogin());
	}

	/**
	 * Creates the order command.
	 *
	 * @param command the command
	 * @return the order created event
	 */
	@Loggable(Loggable.DEBUG)
	@CommandHandler
	public OrderCreatedEvent createOrderCommand(CreateOrderCommand command) {
		OrderCreatedEvent event = new OrderCreatedEvent(command.getCustomerId(),
				command.getOrderId());
		apply(event);
		return event;
	}

	/**
	 * Order created event.
	 *
	 * @param event the event
	 */
	@EventSourcingHandler
	public void orderCreatedEvent(OrderCreatedEvent event) {
		orders.put(event.getOrderId(), new Order(orderLogica));
	}

	/**
	 * Delete order command.
	 *
	 * @param command the command
	 * @return the order deleted event
	 * @throws OrderNotFoundException the order not found exception
	 * @throws OrderNotEmptyException the order not empty exception
	 */
	@Loggable(Loggable.DEBUG)
	@CommandHandler
	public OrderDeletedEvent deleteOrderCommand(DeleteOrderCommand command)
			throws OrderNotFoundException, OrderNotEmptyException {
		if(orders.containsKey(command.getOrderId())) {
			if(orders.get(command.getOrderId()).isOrderEmpty()) {
				OrderDeletedEvent event = new OrderDeletedEvent(
						command.getCustomerId(), command.getOrderId());
				apply(event);
				return event;
			} else throw new OrderNotEmptyException("Order:" + command.getOrderId() + 
					"for customer:" + command.getCustomerId() + " not empty."
							+ "You must remove all products from order");
		} else throw new OrderNotFoundException("Order:" + command.getOrderId() + 
				" not found for customer:" + command.getCustomerId());
	}
	
	/**
	 * Order deleted event.
	 *
	 * @param event the event
	 */
	@EventSourcingHandler
	public void orderDeletedEvent(OrderDeletedEvent event) {
		orders.remove(event.getOrderId());
	}

	/**
	 * Removes the customer command.
	 *
	 * @param command the command
	 * @return the customer removed event
	 */
	@Loggable(Loggable.DEBUG)
	@CommandHandler
	public CustomerRemovedEvent removeCustomerCommand(
			RemoveCustomerCommand command) {
		CustomerRemovedEvent event = new CustomerRemovedEvent(command.getCustomerId());
		apply(event);
		return event;
	}

	/**
	 * Customer removed event.
	 *
	 * @param event the event
	 */
	@EventSourcingHandler
	public void customerRemovedEvent(CustomerRemovedEvent event) {
		markDeleted();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Customer))
			return false;
		Customer other = (Customer) obj;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		return true;
	}
	
	

}
