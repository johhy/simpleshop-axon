package com.github.johhy.simpleshopaxon.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation
	.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import com.github.johhy.simpleshopaxon.core.api.commands.ChangeOrderStatus;
import com.github.johhy.simpleshopaxon.core.api.commands.CreateOrder;
import com.github.johhy.simpleshopaxon.core.api.events.OrderCreated;
import com.github.johhy.simpleshopaxon.core.api.events.OrderStatusChanged;
import com.github.johhy.simpleshopaxon.core.api.exception.DomainStateException;
import com.github.johhy.simpleshopaxon.core.api.shared.Address;
import com.github.johhy.simpleshopaxon.core.api.shared.History;
import com.github.johhy.simpleshopaxon.core.api.shared.OrderStatus;
import com.github.johhy.simpleshopaxon.core.api.shared.Price;
import com.github.johhy.simpleshopaxon.core.api.shared.Product;
import com.jcabi.aspects.Loggable;
import com.jcabi.log.Logger;

/**
 * The Class Order.
 * <p>
 * Domain object.Aggregate Root.
 * Domain command and event handler.
 * Keeps order state and produce logic with order.
 * Returned values in methods use for debug only,
 * apply used for save and publish.
 * 
 * @author johhy
 */
public final class Order extends AbstractAnnotatedAggregateRoot<String> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The order id. */
	@AggregateIdentifier
	private String orderId;
	
	/** The customer id. */
	@SuppressWarnings("unused")
	private String customerId;
	
	/** The ship to. */
	@SuppressWarnings("unused")
	private Address shipTo;
	
	/** The products. */
	@SuppressWarnings("unused")
	private List<Product> products;
	
	/** The history. */
	private List<History> history;
	
	/** The total. */
	@SuppressWarnings("unused")
	private Price total;
	
	/**
	 * Instantiates a new order.
	 */
	public Order() { }

	/**
	 * Instantiates a new order.
	 *
	 * @param command the command
	 */
	@CommandHandler
	public Order(final CreateOrder command) {
		Logger.debug(this, "Order constructur:" + command);
		apply(new OrderCreated(command.getCustomerId(), 
				command.getOrderId(), 
				command.getCreated(),
				command.getShipTo(),
				command.getProducts(),
				command.getTotal()));
	}

	/**
	 * Order created.
	 *
	 * @param event the event
	 */
	@EventSourcingHandler
	private void orderCreated(final OrderCreated event) {
		orderId = event.getOrderId();
		customerId = event.getCustomerId();
		shipTo = event.getShipTo();
		products = event.getProducts();
		history = new ArrayList<History>();
		history.add(new History(event.getCreated(), OrderStatus.CREATED));
		total = event.getTotal();
		Logger.debug(this, "Order created:" + event.getOrderId());
	}

	/**
	 * Change status.
	 *
	 * @param command the command
	 * @return the order status changed
	 */
	@CommandHandler
	@Loggable(trim = false, prepend = true, value = Loggable.DEBUG)
	private OrderStatusChanged changeStatus(final ChangeOrderStatus command) {
		if (isOrderActive()) {
			History newHistory = command.getHistory();
			if (isHistoryNew(newHistory)) {
				if (isHistoryInFuture(newHistory)) {
					history.add(newHistory);
					OrderStatusChanged event =
							new OrderStatusChanged(
								command.getOrderId(), history);
					apply(event);
					return event;
				} else {
				    throw new DomainStateException("Order:" 
					    + orderId + " status in past than last");
				}
			} else {
			    throw new DomainStateException("Order:" + orderId 
				    + " status exists");
			}
		} else {
		    throw new DomainStateException("Order:" 
			    + orderId + " are closed");
		}
	}
	
	
	/**
	 * Status changed.
	 *
	 * @param event the event
	 */
	@EventSourcingHandler
	private void statusChanged(final OrderStatusChanged event) {
		history = event.getHistory();
	}
	
	/**
	 * Checks if is order active.
	 *
	 * @return true, if is order active
	 */
	@Loggable(trim = false, prepend = false, value = Loggable.DEBUG)
	private boolean isOrderActive() {
		return !history.contains(new History(new Date(), OrderStatus.CLOSED));
	}
	
	/**
	 * Checks if is history new.
	 *
	 * @param historyToCheck the history to check
	 * @return true, if is history new
	 */
	@Loggable(trim = false, prepend = false, value = Loggable.DEBUG)
	private boolean isHistoryNew(final History historyToCheck) {
		return !history.contains(historyToCheck);
	}
	
	/**
	 * Checks if is history in future.
	 *
	 * @param newHistory the new history
	 * @return true, if is history in future
	 */
	@Loggable(trim = false, prepend = false, value = Loggable.DEBUG)
	private boolean isHistoryInFuture(final History newHistory) {
		Date newDate = newHistory.getEventDate();
		//Logger.debug(this, "New date:" + newDate);
		History lastHistory = history.get(history.size() - 1);
		Date lastDate = lastHistory.getEventDate();
		//Logger.debug(this, "Last date:" + lastDate);
		return newDate.after(lastDate);
	}

	
}
