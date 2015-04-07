package com.github.johhy.simpleshopaxon.query.customer;

import java.util.List;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.johhy.simpleshopaxon.core.events.customer.CustomerCreatedEvent;
import com.github.johhy.simpleshopaxon.core.events.customer.CustomerInfoChangedEvent;
import com.github.johhy.simpleshopaxon.core.events.customer.CustomerRemovedEvent;
import com.github.johhy.simpleshopaxon.core.events.customer.OrderCreatedEvent;
import com.github.johhy.simpleshopaxon.core.events.customer.OrderDeletedEvent;
import com.github.johhy.simpleshopaxon.core.events.customer.ProductAddedToOrderEvent;
import com.github.johhy.simpleshopaxon.core.events.customer.ProductRemovedFromOrderEvent;
import com.jcabi.aspects.Loggable;

/**
 * @author johhy
 *
 ** Event listener customer for query model
 */
@Service
public class CustomerEventListener {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CustomerInfoRepository customerInfoRepository;
	
	@Loggable(Loggable.DEBUG)
	@EventHandler
	public CustomerTable handle(CustomerCreatedEvent event) {
		CustomerTable ct = new CustomerTable();
		ct.setCustomerId(event.getCustomerId());
		customerRepository.save(ct);
		createCustomerInfo(event);
		return ct;
	}
	
	@Loggable(Loggable.DEBUG)
	public CustomerInfoTable createCustomerInfo(CustomerCreatedEvent event) {
		CustomerInfoTable cit = new CustomerInfoTable();
		cit.setCustomerId(event.getCustomerId());
		cit.setCustomerLogin(event.getCustomerLogin());
		cit.setCustomerName(event.getCustomerName());
		customerInfoRepository.save(cit);
		return cit;
	}
	
	@Loggable(Loggable.DEBUG)
	@EventHandler
	public List<CustomerTable> handle(CustomerRemovedEvent event) {
		//must return one but for debug all
		List<CustomerTable> foundCt = customerRepository
				.findByCustomerId(event.getCustomerId());
		customerRepository.delete(foundCt);
		removeCustomerInfo(event);
		return foundCt;
	}
	
	@Loggable(Loggable.DEBUG)
	public CustomerInfoTable removeCustomerInfo(CustomerRemovedEvent event) {
		//must never return null
		CustomerInfoTable foundCit = customerInfoRepository
				.findByCustomerId(event.getCustomerId());
		customerInfoRepository.delete(foundCit);
		return foundCit;
	}
	
	@Loggable(Loggable.DEBUG)
	@EventHandler
	public CustomerInfoTable handle(CustomerInfoChangedEvent event) {
		//must never return null
		CustomerInfoTable foundCit = customerInfoRepository
				.findByCustomerId(event.getCustomerId());
		foundCit.setCustomerLogin(event.getCustomerLogin());
		foundCit.setCustomerName(event.getCustomerName());
		customerInfoRepository.save(foundCit);
		return foundCit;
	}
	
	@Loggable(Loggable.DEBUG)
	@EventHandler
	public CustomerTable handle(OrderCreatedEvent event) {
		CustomerTable ct = new CustomerTable();
		ct.setCustomerId(event.getCustomerId());
		ct.setOrderId(event.getOrderId());
		customerRepository.save(ct);
		return ct;
	}
	
	@Loggable(Loggable.DEBUG)
	@EventHandler
	public List<CustomerTable> handle(OrderDeletedEvent event) {
		//must return one but for debug return all
		List<CustomerTable> foundCt = customerRepository
				.findByCustomerIdAndOrderId(event.getCustomerId(), 
						event.getOrderId());
		customerRepository.delete(foundCt);
		return foundCt;
	}
	
	@Loggable(Loggable.DEBUG)
	@EventHandler
	public CustomerTable handle(ProductAddedToOrderEvent event) {
		CustomerTable ct = new CustomerTable();
		ct.setCustomerId(event.getCustomerId());
		ct.setOrderId(event.getOrderId());
		ct.setCodeOfProduct(event.getCodeOfProduct());
		ct.setQuantity(event.getQuantity());
		ct.setPrice(event.getPrice());
		customerRepository.save(ct);
		return ct;
	}
	
	@Loggable(Loggable.DEBUG)
	@EventHandler
	public CustomerTable handle(ProductRemovedFromOrderEvent event) {
		//must never return null
		CustomerTable foundCt = customerRepository
				.findByCustomerIdAndOrderIdAndCodeOfProduct(
						event.getCustomerId(), 
						event.getOrderId(), 
						event.getCodeOfProduct());
		customerRepository.delete(foundCt);
		return foundCt;
	}
}
