package com.github.johhy.simpleshopaxon.core.api.events;

import java.util.Date;

import com.github.johhy.simpleshopaxon.core.api.shared.Address;

/**
 * The Class CustomerCreated.
 * 
 * @author johhy
 */
public class CustomerCreated extends AbstractCustomerEvent {

	/** The address. */
	private final Address address;
	
	/** The created. */
	private final Date created;
	
	/**
	 * Instantiates a new customer created.
	 *
	 * @param customerId the customer id
	 * @param customerCreatedCustomer the customer created customer
	 * @param customerCreatedDate the customer created date
	 */
	public CustomerCreated(final String customerId, 
		final Address customerCreatedCustomer, 
		final Date customerCreatedDate) {
		super(customerId);
		this.address = customerCreatedCustomer;
		this.created = customerCreatedDate;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public final Address getAddress() {
		return address;
	}

	/**
	 * Gets the created.
	 *
	 * @return the created
	 */
	public final Date getCreated() {
		return created;
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.api.events
	 * .AbstractCustomerEvent#toString()
	 */
	@Override
	public final String toString() {
		return "CustomerCreated [address=" + address 
			+ ", created=" + created 
			+ ", customerId=" + getCustomerId()
				+ "]";
	}
	
}
