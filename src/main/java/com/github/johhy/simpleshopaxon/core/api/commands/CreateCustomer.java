package com.github.johhy.simpleshopaxon.core.api.commands;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.github.johhy.simpleshopaxon.core.api.shared.Address;

/**
 * The Class CreateCustomer.
 * 
 * @author johhy
 */
public class CreateCustomer extends CustomerCommand {

	/** The address. */
	@NotNull
	@Valid
	private final Address address;
	
	/** The created. */
	@NotNull
	private final Date created;

	/**
	 * Instantiates a new creates the customer.
	 *
	 * @param customerId the customer id
	 * @param customerAddress the customer address
	 * @param customerCreatedDate the customer created date
	 */
	public CreateCustomer(final String customerId, final 
		Address customerAddress, final Date customerCreatedDate) {
		super(customerId);
		this.address = customerAddress;
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
	 * @see com.github.johhy.simpleshopaxon.core.api.commands
	 * .CustomerCommand#toString()
	 */
	@Override
	public final String toString() {
		return "CreateCustomer [address=" + address 
			+ ", created=" + created + ", customerId=" + getCustomerId()
				+ "]";
	}

	
}
