package com.github.johhy.simpleshopaxon.core.api.commands;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotEmpty;

import com.github.johhy.simpleshopaxon.core.infra.Find;
import com.github.johhy.simpleshopaxon.query.repository.CustomerTableRepository;

/**
 * The Class CustomerCommand.
 * 
 * @author johhy
 */
public abstract class CustomerCommand {

	/** The customer id. */
	@NotEmpty
	@Find(repository = CustomerTableRepository.class,
		methodName = "findByCustomerId", mustExists = true)
	@TargetAggregateIdentifier
	private final String customerId;

	/**
	 * Instantiates a new customer command.
	 *
	 * @param customerIdToReceive the customer id to receive
	 */
	public CustomerCommand(final String customerIdToReceive) {
		super();
		this.customerId = customerIdToReceive;
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
