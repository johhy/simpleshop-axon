package com.github.johhy.simpleshopaxon.core.api.commands;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotBlank;

import com.github.johhy.simpleshopaxon.core.api.shared.History;

/**
 * The Class ChangeOrderStatus.
 * 
 * @author johhy
 */
public class ChangeOrderStatus {

	/** The order id. */
	@NotBlank
	@TargetAggregateIdentifier
	private final String orderId;
	
	/** The history. */
	@NotNull
	@Valid
	private final History history;

	/**
	 * Instantiates a new change order status.
	 *
	 * @param orderIdToChange the order id to change
	 * @param newHistory the new history
	 */
	public ChangeOrderStatus(final String orderIdToChange, 
		final History newHistory) {
		super();
		this.orderId = orderIdToChange;
		this.history = newHistory;
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
	 * Gets the history.
	 *
	 * @return the history
	 */
	public final History getHistory() {
		return history;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		return "ChangeOrderStatus [orderId=" + orderId 
			+ ", history=" + history + "]";
	}
	


}
