package com.github.johhy.simpleshopaxon.core.api.commands;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotBlank;

import com.github.johhy.simpleshopaxon.core.api.shared.Address;

/**
 * The Class CreateOrderForCustomer.
 * 
 * @author johhy
 */
public class CreateOrderForCustomer {

	/** The customer id. */
	@NotBlank
	@TargetAggregateIdentifier
	private final String customerId;
	
	/** The order id. */
	@NotBlank
	private final String orderId;
	
	/** The created. */
	@NotNull
	private final Date created;
	
	/** The ship to. */
	@NotNull
	@Valid
	private final Address shipTo;

	/**
	 * Instantiates a new creates the order for customer.
	 *
	 * @param customerIdOwnOrder the customer id own order
	 * @param orderIdCreated the order id created
	 * @param orderCreatedDate the order created date
	 * @param addressToShip the address to ship
	 */
	public CreateOrderForCustomer(final String customerIdOwnOrder, 
		final String orderIdCreated, final Date orderCreatedDate, 
		final Address addressToShip) {
		super();
		this.customerId = customerIdOwnOrder;
		this.orderId = orderIdCreated;
		this.created = orderCreatedDate;
		this.shipTo = addressToShip;
	}

	/**
	 * Gets the customer id.
	 *
	 * @return the customer id
	 */
	public final String getCustomerId() {
		return customerId;
	}

	/**
	 * Gets the order id.
	 *
	 * @return the order id
	 */
	public final String getOrderId() {
		return orderId;
	}

	/**
	 * Gets the created.
	 *
	 * @return the created
	 */
	public final Date getCreated() {
		return created;
	}

	/**
	 * Gets the ship to.
	 *
	 * @return the ship to
	 */
	public final Address getShipTo() {
		return shipTo;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		return "CreateOrderForCustomer [customerId=" + customerId 
			+ ", orderId=" + orderId
			+ ", created=" + created
			+ ", shipTo=" + shipTo + "]";
	}
}
