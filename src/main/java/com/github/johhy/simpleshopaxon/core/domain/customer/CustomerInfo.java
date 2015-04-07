package com.github.johhy.simpleshopaxon.core.domain.customer;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import com.github.johhy.simpleshopaxon.core.commands.customer.ChangeCustomerInfoCommand;
import com.github.johhy.simpleshopaxon.core.events.customer.CustomerInfoChangedEvent;
import com.jcabi.aspects.Loggable;

/**
 * @author johhy
 * <p>
 * Class keeps customer info
 */
public class CustomerInfo extends AbstractAnnotatedEntity {
	
	/** The customer name. */
	protected String customerName;
	
	/** The customer login. */
	protected String customerLogin;
	
	public CustomerInfo(String customerName, String customerLogin) {
		this.customerName = customerName;
		this.customerLogin = customerLogin;
	}
	
	@Loggable(Loggable.DEBUG)
	@CommandHandler
	public CustomerInfoChangedEvent 
				changeCustomerInfo(ChangeCustomerInfoCommand command) {
		CustomerInfoChangedEvent event =
				new CustomerInfoChangedEvent(command.getCustomerId(),
				command.getCustomerName(), command.getCustomerLogin());
		apply(event);
		return event;
	}
	
	@EventSourcingHandler
	public void customerInfoChanged(CustomerInfoChangedEvent event) {
		this.customerName= event.getCustomerName();
		this.customerLogin = event.getCustomerLogin();
	}
}
