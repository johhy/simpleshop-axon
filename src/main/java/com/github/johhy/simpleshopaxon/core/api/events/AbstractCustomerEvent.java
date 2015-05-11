package com.github.johhy.simpleshopaxon.core.api.events;

/**
 * The Class AbstractCustomerEvent.
 * 
 * @author johhy
 */
public abstract class AbstractCustomerEvent {

	/** The customer id. */
	private final String customerId;

	/**
	 * Instantiates a new abstract customer event.
	 *
	 * @param customerIdDoEvent the customer id do event
	 */
	public AbstractCustomerEvent(final String customerIdDoEvent) {
		super();
		this.customerId = customerIdDoEvent;
	}

	/**
	 * Gets the customer id.
	 *
	 * @return the customer id
	 */
	public final String getCustomerId() {
		return customerId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public abstract String toString();
	
}
