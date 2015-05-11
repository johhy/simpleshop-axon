package com.github.johhy.simpleshopaxon.query.listeners;

import java.util.List;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.johhy.simpleshopaxon.core.api.events.CustomerAddressChanged;
import com.github.johhy.simpleshopaxon.core.api.events.CustomerCreated;
import com.github.johhy.simpleshopaxon.core.api.events.ShoppingCartChanged;
import com.github.johhy.simpleshopaxon.core.api.shared.Product;
import com.github.johhy.simpleshopaxon.query.repository.CustomerTableRepository;
import com.github.johhy.simpleshopaxon.query.tables.CustomerTable;
import com.jcabi.aspects.Loggable;


/**
 * The listener interface for receiving customer events.
 * The class that is interested in processing a customer
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addCustomerListener</code> method. When
 * the customer event occurs, that object's appropriate
 * method is invoked.
 *
 * @see com.github.johhy.simpleshopaxon.core.api.events.AbstractCustomerEvent
 * 
 * @author johhy
 */
@Service
public class CustomerListener {

	/** The CustomerTableRepository. */
	@Autowired
	private CustomerTableRepository ctRepo;
	
	/**
	 * Creates the customer.
	 *
	 * @param event the event
	 * @return the customer table
	 */
	@EventHandler
	@Loggable(trim = false, prepend = false, value = Loggable.DEBUG)
	public CustomerTable createCustomer(final CustomerCreated event) {
		CustomerTable ct = new CustomerTable();
		ct.setCustomerId(event.getCustomerId());
		ct.setAddress(event.getAddress().getAddress());
		ct.setCreated(event.getCreated());
		return ctRepo.save(ct);
	}
	
	/**
	 * Products changed.
	 *
	 * @param event the event
	 */
	@EventHandler
	@Loggable(trim = false, prepend = true, value = Loggable.DEBUG)
	public void productsChanged(final ShoppingCartChanged event) {
		List<CustomerTable> ctl = ctRepo
			.findByCustomerId(event.getCustomerId());
		if (!ctl.isEmpty()) {
			CustomerTable ct = ctl.get(0);
			ctRepo.delete(ctl);
			for (Product p:event.getProducts()) {
				CustomerTable nct = new CustomerTable();
				nct.setCustomerId(ct.getCustomerId());
				nct.setAddress(ct.getAddress());
				nct.setCreated(ct.getCreated());
				nct.setProductId(p.getProductId());
				nct.setQuantity(p.getQuantity());
				nct.setPrice(p.getPrice().getValue());
				saveTable(nct);
			}
		}
		
	}
	
	/**
	 * Save table.
	 *
	 * @param table the table
	 * @return the customer table
	 */
	@Loggable(trim = false, prepend = false, value = Loggable.DEBUG)
	private CustomerTable saveTable(final CustomerTable table) {
		return ctRepo.save(table);
	}

	/**
	 * Address changed.
	 *
	 * @param event the event
	 * @return the CustomerTables
	 */
	@EventHandler
	@Loggable(trim = false, prepend = false, value = Loggable.DEBUG)
	public Iterable<CustomerTable> 
	addressChanged(final CustomerAddressChanged event) {
		List<CustomerTable> ctl = ctRepo
			.findByCustomerId(event.getCustomerId());
		for (CustomerTable ct:ctl) { 
			ct.setAddress(event.getAddress().getAddress());
		}
		return ctRepo.save(ctl);
	}
}
