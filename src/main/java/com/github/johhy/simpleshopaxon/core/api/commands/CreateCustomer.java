package com.github.johhy.simpleshopaxon.core.api.commands;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotEmpty;

import com.github.johhy.simpleshopaxon.core.api.shared.Address;
import com.github.johhy.simpleshopaxon.core.infra.Find;
import com.github.johhy.simpleshopaxon.query.repository.CustomerTableRepository;

/**
 * The Class CreateCustomer.
 * 
 * @author johhy
 */
public class CreateCustomer {
    
    	/** The customer id. */
	@NotEmpty
	@Find(repository = CustomerTableRepository.class,
		methodName = "findByCustomerId", mustExists = false)
	@TargetAggregateIdentifier
	private final String customerId;

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
		this.customerId = customerId;
		this.address = customerAddress;
		this.created = customerCreatedDate;
	}

	/**
	 * @return the customerId
	 */
	public String getCustomerId() {
	    return customerId;
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
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
	    return "CreateCustomer [customerId=" + customerId 
		    + ", address=" + address 
		    + ", created=" + created + "]";
	}



	
}
