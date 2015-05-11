package com.github.johhy.simpleshopaxon.core.api.events;

import com.github.johhy.simpleshopaxon.core.api.shared.Address;

/**
 * The Class CustomerAddressChanged.
 * 
 * @author johhy
 */
public class CustomerAddressChanged extends AbstractCustomerEvent {

	/** The address. */
	private final Address address;
	
	/**
	 * Instantiates a new customer address changed.
	 *
	 * @param customerId the customer id
	 * @param customerNewAddress the customer new address
	 */
	public CustomerAddressChanged(final String customerId, 
		final Address customerNewAddress) {
		super(customerId);
		this.address = customerNewAddress;
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
	 * @see com.github.johhy.simpleshopaxon.core.api.events
	 * .AbstractCustomerEvent#toString()
	 */
	@Override
	public final String toString() {
		return "CustomerAddressChanged [address=" 
			+ address + ", customerId=" + getCustomerId() + "]";
	}
	

}
