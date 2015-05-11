package com.github.johhy.simpleshopaxon.core;


import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.johhy.simpleshopaxon.core.api.commands.CreateOrder;
import com.github.johhy.simpleshopaxon.core.api.events.OrderCreatedForCustomer;
import com.jcabi.aspects.Loggable;
import com.jcabi.log.Logger;

/**
 * The Class OrderCreatorService.
 * <p>
 * Domain service used for create order for customer.
 * 
 * @author johhy
 */
@Service
public class OrderCreatorService {

	/** The command bus. */
	private CommandBus commandBus;

	/**
	 * Order created for customer.
	 *
	 * @param event the event
	 */
	@EventHandler
	@Loggable(trim = false, prepend = false, value = Loggable.DEBUG)
	public void orderCreatedForCustomer(final OrderCreatedForCustomer event) {
		try {
			commandBus.dispatch(new GenericCommandMessage<CreateOrder>(
					new CreateOrder(
							event.getCustomerId(), 
							event.getOrderId(),
							event.getCreated(),
							event.getShipTo(),
							event.getProducts(),
							event.getTotal())));
		} catch (Exception e) {
			e.printStackTrace();
			Logger.debug(this, e.getMessage());
		}
	}
	

	/**
	 * Sets the command bus.
	 *
	 * @param injectedCommandBus the new command bus
	 */
	@Autowired
	public void setCommandBus(final CommandBus injectedCommandBus) {
		this.commandBus = injectedCommandBus;
	}
}
