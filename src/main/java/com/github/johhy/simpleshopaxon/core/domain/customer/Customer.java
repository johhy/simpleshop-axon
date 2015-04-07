package com.github.johhy.simpleshopaxon.core.domain.customer;

import java.util.Map;

import javax.persistence.Transient;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.commandhandling.annotation.CommandHandlingMember;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcedMember;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import com.github.johhy.simpleshopaxon.core.commands.customer.AddProductToOrderCommand;
import com.github.johhy.simpleshopaxon.core.commands.customer.CreateCustomerCommand;
import com.github.johhy.simpleshopaxon.core.commands.customer.CreateOrderCommand;
import com.github.johhy.simpleshopaxon.core.commands.customer.DeleteOrderCommand;
import com.github.johhy.simpleshopaxon.core.commands.customer.RemoveCustomerCommand;
import com.github.johhy.simpleshopaxon.core.commands.customer.RemoveProductFromOrderCommand;
import com.github.johhy.simpleshopaxon.core.events.customer.CustomerCreatedEvent;
import com.github.johhy.simpleshopaxon.core.events.customer.CustomerRemovedEvent;
import com.github.johhy.simpleshopaxon.core.events.customer.OrderCreatedEvent;
import com.github.johhy.simpleshopaxon.core.events.customer.OrderDeletedEvent;
import com.github.johhy.simpleshopaxon.core.events.customer.ProductAddedToOrderEvent;
import com.github.johhy.simpleshopaxon.core.events.customer.ProductRemovedFromOrderEvent;
import com.github.johhy.simpleshopaxon.core.exceptions.customer.AmountProductToRemoveNotEqualInOrderException;
import com.github.johhy.simpleshopaxon.core.exceptions.customer.OrderNotEmptyException;
import com.github.johhy.simpleshopaxon.core.exceptions.customer.OrderNotFoundException;
import com.github.johhy.simpleshopaxon.core.exceptions.customer.ProductExistsException;
import com.github.johhy.simpleshopaxon.core.exceptions.customer.ProductNotFoundException;
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

	/**
	 * 
	 */
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
	
	@EventSourcedMember
	private Map<String, Order> orders; 
	
	public void setOrders(Map<String, Order> orders) {
		this.orders = orders;
		logica.setOrders(orders);
	}

	/** The customer info. */
	@CommandHandlingMember
	@EventSourcedMember
	private CustomerInfo customerInfo;
	
	public Customer() {};
	
	public Customer(CustomerLogica logica, OrderLogica orderLogica) {
		this.logica = logica;
		this.orderLogica = orderLogica;
	}
	
	@CommandHandler
	public Customer(CreateCustomerCommand command) {
		apply(new CustomerCreatedEvent(command.getCustomerId(),
				command.getCustomerName(),command.getCustomerLogin()));
	}
	
	@EventSourcingHandler
	public void createdCustomerEvent(CustomerCreatedEvent event) {
		this.customerId = event.getCustomerId();
		this.customerInfo = new CustomerInfo(event.getCustomerName(), 
				event.getCustomerLogin());
	}

	@Loggable(Loggable.DEBUG)
	@CommandHandler
	public OrderCreatedEvent createOrderCommand(CreateOrderCommand command) {
		OrderCreatedEvent event = new OrderCreatedEvent(command.getCustomerId(),
				command.getOrderId());
		apply(event);
		return event;
	}

	@EventSourcingHandler
	public void orderCreatedEvent(OrderCreatedEvent event) {
		logica.createOrder(event.getOrderId(), orderLogica);

	}

	@Loggable(Loggable.DEBUG)
	@CommandHandler
	public OrderDeletedEvent deleteOrderCommand(DeleteOrderCommand command)
			throws OrderNotFoundException, OrderNotEmptyException {
		if(logica.isOrderExists(command.getOrderId())) {
			if(logica.findOrder(command.getOrderId()).isOrderEmpty()) {
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
	
	@EventSourcingHandler
	public void orderDeletedEvent(OrderDeletedEvent event) {
		logica.removeOrder(event.getOrderId());
	}

	@Loggable(Loggable.DEBUG)
	@CommandHandler
	public ProductAddedToOrderEvent addProductToOrderCommand(
			AddProductToOrderCommand command) throws OrderNotFoundException,
			ProductExistsException {
		if(logica.isOrderExists(command.getOrderId())) {
			Order order = logica.findOrder(command.getOrderId());
				return order.addProductToOrderCommand(command);
		} else throw new OrderNotFoundException("Order:" + command.getOrderId() + 
				" not found for customer:" + command.getCustomerId());
	}

	@Loggable(Loggable.DEBUG)
	@CommandHandler
	public ProductRemovedFromOrderEvent removeProductFromOrderCommand(
			RemoveProductFromOrderCommand command)
			throws ProductNotFoundException, OrderNotFoundException,
				AmountProductToRemoveNotEqualInOrderException {
		if(logica.isOrderExists(command.getOrderId())) {
			Order order = logica.findOrder(command.getOrderId());
					//delegates to order remove product
					return order.removeProductFromOrderCommand(command);
		} else throw new OrderNotFoundException("Order:" + command.getOrderId() + 
				" not found for customer:" + command.getCustomerId());
	}

	@Loggable(Loggable.DEBUG)
	@CommandHandler
	public CustomerRemovedEvent removeCustomerCommand(
			RemoveCustomerCommand command) {
		CustomerRemovedEvent event = new CustomerRemovedEvent(command.getCustomerId());
		apply(event);
		return event;
	}

	@EventSourcingHandler
	public void customerRemovedEvent(CustomerRemovedEvent event) {
		markDeleted();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		return result;
	}

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
