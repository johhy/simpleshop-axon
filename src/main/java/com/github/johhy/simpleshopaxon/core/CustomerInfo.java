package com.github.johhy.simpleshopaxon.core;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import com.github.johhy.simpleshopaxon.core.api.commands.ChangeCustomerAddress;
import com.github.johhy.simpleshopaxon.core.api.events.CustomerAddressChanged;
import com.github.johhy.simpleshopaxon.core.api.shared.Address;
import com.jcabi.aspects.Loggable;
import com.jcabi.log.Logger;

/**
 * The Class CustomerInfo.
 * <p>
 * Domain object.Entity belongs to Customer.
 * Keeps customer information for example Address.
 * Returned values in methods use for debug only,
 * apply used for save and publish.
 * 
 * @author johhy
 */
public class CustomerInfo extends AbstractAnnotatedEntity {
	
	/** The address. */
	@SuppressWarnings("unused")
	private Address address;
	
	/**
	 * Instantiates a new customer info.
	 *
	 * @param customerAddress the address
	 */
	public CustomerInfo(final Address customerAddress) {
		Logger.debug(this, "CustomerInfo constructor:" 
			+ customerAddress);
		this.address = customerAddress;
	}
	
	/**
	 * Change address.
	 *
	 * @param command the command
	 * @return the customer address changed
	 */
	@CommandHandler
	@Loggable(trim = false, prepend = false, value = Loggable.DEBUG)
	private CustomerAddressChanged 
		changeAddress(final ChangeCustomerAddress command) {
		CustomerAddressChanged event =
				new CustomerAddressChanged(command.getCustomerId(), 
						command.getAddress());
		apply(event);
		return event;
	}
	
	/**
	 * Address changed.
	 *
	 * @param event the event
	 */
	@EventSourcingHandler
	private void addressChanged(final CustomerAddressChanged event) {
		address = event.getAddress();
	}

}
