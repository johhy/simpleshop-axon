package com.github.johhy.simpleshopaxon.query.listeners;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.johhy.simpleshopaxon.core.api.events.ProductCellCreated;
import com.github.johhy.simpleshopaxon.core.api.events.ProductGivenToCustomer;
import com.github.johhy.simpleshopaxon.core.api.events
						.AbstractProductInProductCellChanged;
import com.github.johhy.simpleshopaxon.core.api.events
						.ProductReturnedFromCustomer;
import com.github.johhy.simpleshopaxon.query.repository.ProductTableRepository;
import com.github.johhy.simpleshopaxon.query.tables.ProductTable;
import com.jcabi.aspects.Loggable;

/**
 * The listener interface for receiving productTable events.
 * The class that is interested in processing a productTable
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addProductTableListener</code> method. When
 * the productTable event occurs, that object's appropriate
 * method is invoked.
 *
 * @see com.github.johhy.simpleshopaxon.core.api.events.AbstractProductCellEvent
 * 
 * @author johhy
 */
@Service
public class ProductTableListener {

	/** The ProductTableRepository repo. */
	@Autowired
	private ProductTableRepository ptRepo;
	
	/**
	 * Creates the product.
	 *
	 * @param event the event
	 * @return the product table
	 */
	@EventHandler
	@Loggable(trim = false, prepend = false, value = Loggable.DEBUG)
	public ProductTable createProduct(final ProductCellCreated event) {
		ProductTable pt = new ProductTable();
		pt.setProductId(event.getProductId());
		pt.setPrice(event.getProduct().getPrice().getValue());
		pt.setQuantity(event.getProduct().getQuantity());
		return ptRepo.save(pt);
	}
	
	/**
	 * Product changed.
	 *
	 * @param event the event
	 * @return the product table
	 */
	@EventHandler
	@Loggable(trim = false, prepend = false, value = Loggable.DEBUG)
	public ProductTable 
	productChanged(final AbstractProductInProductCellChanged event) {
		ProductTable pt = ptRepo.findByProductId(event.getProductId());
		pt.setPrice(event.getProduct().getPrice().getValue());
		pt.setQuantity(event.getProduct().getQuantity());
		return ptRepo.save(pt);
	}
	
	/**
	 * Product given.
	 *
	 * @param event the event
	 * @return the product table
	 */
	@EventHandler
	@Loggable(trim = false, prepend = false, value = Loggable.DEBUG)
	public ProductTable productGiven(final ProductGivenToCustomer event) {
		ProductTable pt = ptRepo.findByProductId(event.getProductId());
		pt.setPrice(event.getProduct().getPrice().getValue());
		pt.setQuantity(event.getProduct().getQuantity());
		return ptRepo.save(pt);
	}
	
	/**
	 * Product given.
	 *
	 * @param event the event
	 * @return the product table
	 */
	@EventHandler
	@Loggable(trim = false, prepend = false, value = Loggable.DEBUG)
	public ProductTable productGiven(final ProductReturnedFromCustomer event) {
		ProductTable pt = ptRepo.findByProductId(event.getProductId());
		pt.setPrice(event.getProduct().getPrice().getValue());
		pt.setQuantity(event.getProduct().getQuantity());
		return ptRepo.save(pt);
	}
}
