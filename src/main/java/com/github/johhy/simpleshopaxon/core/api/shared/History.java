package com.github.johhy.simpleshopaxon.core.api.shared;

import java.util.Date;

import javax.validation.constraints.NotNull;


/**
 * The Class History.
 * <p>
 * Domain object.Value object.
 * Keeps history.
 * 
 * @author johhy
 */
public class History {
	
	/** The event date. */
	@NotNull
	private final Date eventDate;
	
	/** The order status. */
	@NotNull
	private final OrderStatus orderStatus;

	/**
	 * Instantiates a new history.
	 *
	 * @param dateWhenEventProcess the date when event process
	 * @param statusOfOrder the status of order
	 */
	public History(final Date dateWhenEventProcess, 
		final OrderStatus statusOfOrder) {
		super();
		this.eventDate = dateWhenEventProcess;
		this.orderStatus = statusOfOrder;
	}

	/**
	 * Gets the event date.
	 *
	 * @return the event date
	 */
	public final Date getEventDate() {
		return eventDate;
	}

	/**
	 * Gets the order status.
	 *
	 * @return the order status
	 */
	public final OrderStatus getOrderStatus() {
		return orderStatus;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		int hash = 0;
		if (orderStatus != null) {
		    hash = orderStatus.hashCode();
		}
		result = prime * result + hash;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals(final Object obj) {
		if (this == obj) {
		    return true;
		}
		if (obj == null) {
		    return false;
		}
		if (!(obj instanceof History)) {
		    return false;
		}
		History other = (History) obj;
		if (orderStatus != other.orderStatus) {
		    return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		return "History [eventDate=" + eventDate 
			+ ", orderStatus=" + orderStatus + "]";
	}
	
	

}
