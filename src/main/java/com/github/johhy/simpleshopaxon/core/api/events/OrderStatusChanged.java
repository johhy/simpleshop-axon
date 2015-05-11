package com.github.johhy.simpleshopaxon.core.api.events;

import java.util.List;

import com.github.johhy.simpleshopaxon.core.api.shared.History;

/**
 * The Class OrderStatusChanged.
 * 
 * @author johhy
 */
public class OrderStatusChanged {

	/** The order id. */
	private final String orderId;
	
	/** The history. */
	private final List<History> history;

	/**
	 * Instantiates a new order status changed.
	 *
	 * @param orderIdToChangeStatus the order id to change status
	 * @param newHistory the new history
	 */
	public OrderStatusChanged(final String orderIdToChangeStatus, 
		final List<History> newHistory) {
		super();
		this.orderId = orderIdToChangeStatus;
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
	public final List<History> getHistory() {
		return history;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		return "OrderStatusChanged [orderId=" + orderId 
			+ ", history=" + history + "]";
	}
	
}