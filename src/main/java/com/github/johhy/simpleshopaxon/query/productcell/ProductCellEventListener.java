package com.github.johhy.simpleshopaxon.query.productcell;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.johhy.simpleshopaxon.core.events.productcell.PriceProductChangedEvent;
import com.github.johhy.simpleshopaxon.core.events.productcell.ProductCellCreatedEvent;
import com.github.johhy.simpleshopaxon.core.events.productcell.ProductCellRemovedEvent;
import com.github.johhy.simpleshopaxon.core.events.productcell.ProductQuantityChangedInProductCellEvent;
import com.github.johhy.simpleshopaxon.core.events.productcell.ReservedProductChangedInProductCellEvent;
import com.jcabi.aspects.Loggable;
/**
 * 
 * @author johhy
 *
 * Event listener product cell for query model
 */
@Service
public class ProductCellEventListener {
	
	@Autowired
	private ProductCellRepository productCellRepository;
	
	@Autowired
	private ReservedProductRepository reservedProductRepository;
	
	@Loggable(Loggable.DEBUG)
	@EventHandler
	public ProductCellTable handle(ProductCellCreatedEvent event) {
		ProductCellTable pct = new ProductCellTable();
		pct.setCodeOfProduct(event.getCodeOfProduct());
		pct.setQuantity(0);
		pct.setPrice(0.0);
		productCellRepository.save(pct);
		return pct;
	}
	
	@Loggable(Loggable.DEBUG)
	@EventHandler
	public ProductCellTable handle(ProductQuantityChangedInProductCellEvent event) {
		ProductCellTable pct = productCellRepository.findByCodeOfProduct(event.getCodeOfProduct());
		pct.setQuantity(event.getQuantity());
		productCellRepository.save(pct);
		return pct;
	}
	
	@Loggable(Loggable.DEBUG)
	@EventHandler
	public ProductCellTable handle(PriceProductChangedEvent event) {
		ProductCellTable pct = productCellRepository.findByCodeOfProduct(event.getCodeOfProduct());
		pct.setPrice(event.getPrice());
		productCellRepository.save(pct);
		return pct;
	}
	
	@Loggable(Loggable.DEBUG)
	@EventHandler
	public ProductCellTable handle(ProductCellRemovedEvent event) {
		ProductCellTable pct = productCellRepository.findByCodeOfProduct(event.getCodeOfProduct());
		productCellRepository.delete(pct);
		return pct;
	}
	
	@Loggable(Loggable.DEBUG)
	@EventHandler
	public ReservedProductTable handle(ReservedProductChangedInProductCellEvent event) {
		ReservedProductTable rpt = reservedProductRepository
					.findByOrderIdAndCodeOfProduct(event.getOrderId(), event.getCodeOfProduct());
		if(rpt==null&&event.getAmount()!=0) {
			ReservedProductTable newRPT = new ReservedProductTable();
			newRPT.setCodeOfProduct(event.getCodeOfProduct());
			newRPT.setOrderId(event.getOrderId());
			newRPT.setAmount(event.getAmount());
			reservedProductRepository.save(newRPT);
			return newRPT;
		} else if (rpt!=null&&event.getAmount()==0){
			reservedProductRepository.delete(rpt);
		} else if (rpt!=null) {
			rpt.setAmount(event.getAmount());
			reservedProductRepository.save(rpt);
		}
		return rpt;
	}
}
