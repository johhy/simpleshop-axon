package com.github.johhy.simpleshopaxon.core.api.commands;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.github.johhy.simpleshopaxon.core.api.shared.Address;

/**
 * The Class ChangeCustomerAddress.
 * 
 * @author johhy
 */
public class ChangeCustomerAddress extends CustomerCommand {

	/** The address. */
	@NotNull
	@Valid
	private final Address address;
	
	/**
	 * Instantiates a new change customer address.
	 *
	 * @param customerId the customer id
	 * @param newAddress the address
	 */
	public ChangeCustomerAddress(final String customerId,
		final Address newAddress) {
		super(customerId);
		this.address = newAddress;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public final Address getAddress() {
		return address;
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.api.commands
	 * .CustomerCommand#toString()
	 */
	@Override
	public final String toString() {
		return "ChangeCustomerAddress [address=" + address 
			+ ", customerId=" + getCustomerId() + "]";
	}
	
	
}
