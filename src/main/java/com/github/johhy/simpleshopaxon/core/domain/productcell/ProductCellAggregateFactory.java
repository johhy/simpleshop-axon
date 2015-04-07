package com.github.johhy.simpleshopaxon.core.domain.productcell;

import org.axonframework.domain.DomainEventMessage;
import org.axonframework.eventsourcing.GenericAggregateFactory;

/**
 * A factory for creating ProductCellAggregate objects.
 * 
 * @author johhy
 */
public class ProductCellAggregateFactory extends GenericAggregateFactory<ProductCell> {

	/** The product cell logica class. */
	private Class<?> productCellLogicaClass;
	
	/**
	 * Instantiates a new product cell aggregate factory.
	 *
	 * @param clazz the clazz
	 */
	public ProductCellAggregateFactory(Class<?> clazz) {
		super(ProductCell.class);
		this.productCellLogicaClass = clazz;
	}

	/* (non-Javadoc)
	 * @see org.axonframework.eventsourcing.GenericAggregateFactory#doCreateAggregate(java.lang.Object, org.axonframework.domain.DomainEventMessage)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected ProductCell doCreateAggregate(Object aggregateIdentifier, DomainEventMessage firstEvent) {
		ProductCellLogica productCellLogica = null;
		try {
			productCellLogica = (ProductCellLogica) 
					productCellLogicaClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		ProductCell productCell = new ProductCell(productCellLogica);
		return productCell;
	}

	
}
