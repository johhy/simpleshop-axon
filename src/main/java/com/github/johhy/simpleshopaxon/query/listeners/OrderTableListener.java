package com.github.johhy.simpleshopaxon.query.listeners;

import java.util.List;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.johhy.simpleshopaxon.core.api.events.OrderCreated;
import com.github.johhy.simpleshopaxon.core.api.events.OrderStatusChanged;
import com.github.johhy.simpleshopaxon.core.api.shared.OrderStatus;
import com.github.johhy.simpleshopaxon.core.api.shared.Product;
import com.github.johhy.simpleshopaxon.query.repository.OrderTableRepository;
import com.github.johhy.simpleshopaxon.query.tables.OrderTable;
import com.jcabi.aspects.Loggable;

/**
 * The listener interface for receiving orderTable events.
 * The class that is interested in processing a orderTable
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addOrderTableListener</code> method. When
 * the orderTable event occurs, that object's appropriate
 * method is invoked.
 *
 * @see OrderCreated
 * 
 * @author johhy
 */
@Service
public class OrderTableListener {

	/** The OrderTableRepository. */
	@Autowired
	private OrderTableRepository otRepo;
	
	/**
	 * Creates the.
	 *
	 * @param event the event
	 */
	@EventHandler
	@Loggable(trim = false, prepend = true, value = Loggable.DEBUG)
	public void create(final OrderCreated event) {
		for (Product p:event.getProducts()) {
			OrderTable ot = new OrderTable();
			ot.setOrderId(event.getOrderId());
			ot.setCustomerId(event.getCustomerId());
			ot.setShipTo(event.getShipTo().getAddress());
			ot.setCreated(event.getCreated());
			ot.setOrderSatus(OrderStatus.CREATED.toString());
			ot.setTotal(event.getTotal().getValue());
			ot.setProductId(p.getProductId());
			ot.setQuantity(p.getQuantity());
			ot.setPrice(p.getPrice().getValue());
			saveTable(ot);
		}
	}
	
	/**
	 * Save table.
	 *
	 * @param entity the entity
	 * @return the order table
	 */
	@Loggable(trim = false, prepend = false, value = Loggable.DEBUG)
	private OrderTable saveTable(final OrderTable entity) {
		return otRepo.save(entity);
	}
	
	/**
	 * Order status changed.
	 *
	 * @param event the event
	 * @return the OrderTables
	 */
	@EventHandler
	@Loggable(trim = false, prepend = false, value = Loggable.DEBUG)
	public Iterable<OrderTable> 
	orderStatusChanged(final OrderStatusChanged event) {
		List<OrderTable> otl = otRepo.findByOrderId(event.getOrderId());
		int size = event.getHistory().size() - 1;
		for (OrderTable ot:otl) {
			ot.setOrderSatus(event.getHistory().get(size)
					.getOrderStatus().toString());
		}
		return otRepo.save(otl);
	}
}
