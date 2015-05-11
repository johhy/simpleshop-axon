package com.github.johhy.simpleshopaxon.facade;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.axonframework.commandhandling.interceptors.JSR303ViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.johhy.simpleshopaxon.core.api.commands
						.AddProductToProductCell;
import com.github.johhy.simpleshopaxon.core.api.commands
						.ChangeCapacityProductCell;
import com.github.johhy.simpleshopaxon.core.api.commands
						.ChangeCustomerAddress;
import com.github.johhy.simpleshopaxon.core.api.commands.ChangeOrderStatus;
import com.github.johhy.simpleshopaxon.core.api.commands
						.ChangePriceInProductCell;
import com.github.johhy.simpleshopaxon.core.api.commands.ClearShoppingCart;
import com.github.johhy.simpleshopaxon.core.api.commands.CreateCustomer;
import com.github.johhy.simpleshopaxon.core.api.commands.CreateOrderForCustomer;
import com.github.johhy.simpleshopaxon.core.api.commands.CreateProductCell;
import com.github.johhy.simpleshopaxon.core.api.commands.GiveProductToCustomer;
import com.github.johhy.simpleshopaxon.core.api.commands
						.RemoveProductFromProductCell;
import com.github.johhy.simpleshopaxon.core.api.commands
						.ReturnProductFromCustomer;
import com.github.johhy.simpleshopaxon.core.api.exception.DomainStateException;
import com.github.johhy.simpleshopaxon.core.api.shared.Address;
import com.github.johhy.simpleshopaxon.core.api.shared
						.ApplicationCommandGateway;
import com.github.johhy.simpleshopaxon.core.api.shared.History;
import com.github.johhy.simpleshopaxon.core.api.shared.OrderStatus;
import com.github.johhy.simpleshopaxon.core.api.shared.Price;
import com.github.johhy.simpleshopaxon.core.api.shared.Product;
import com.github.johhy.simpleshopaxon.facade.generators.CustomerIdGenerator;
import com.github.johhy.simpleshopaxon.facade.generators.OrderIdGenerator;
import com.github.johhy.simpleshopaxon.query.repository.CustomerTableRepository;
import com.github.johhy.simpleshopaxon.query.repository.OrderTableRepository;
import com.github.johhy.simpleshopaxon.query.repository.ProductTableRepository;
import com.jcabi.aspects.Loggable;
import com.jcabi.log.Logger;

/**
 * The Class FacadeCommandServiceImpl.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, 
	rollbackFor = Exception.class)
public class FacadeCommandServiceImpl implements FacadeCommandService {

	/** The ApplicationCommandGateway. */
	@Autowired
	private ApplicationCommandGateway cg;
	
	/** The ProductTableRepository. */
	@Autowired
	private ProductTableRepository pRepo;
	
	/** The CustomerTableRepository. */
	@Autowired
	private CustomerTableRepository cRepo;
	
	/** The OrderTableRepository. */
	@Autowired
	private OrderTableRepository oRepo;
	
	/** The customer id generator. */
	@Autowired
	private CustomerIdGenerator customerIdGen;
	
	/** The order id generator. */
	@Autowired
	private OrderIdGenerator orderIdGen;
	
	/** The a timeout. */
	private long aTIMEOUT = 2;
	
	/** The a unit. */
	private TimeUnit aUNIT = TimeUnit.SECONDS;
	
	/** The a shipped. */
	private final static int aSHIPPED = 1;
	
	/** The a delivered. */
	private final static int aDELIVERED = 2;
	
	/** The a closed. */
	private final static int aCLOSED = 0;
	
	/**
	 * Instantiates a new facade command service impl.
	 */
	public FacadeCommandServiceImpl() {
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.facade
	 * .FacadeCommandService#createProduct(java.lang.String, 
	 * java.lang.Integer, java.lang.Double)
	 */
	@Override
	@Loggable(trim = false, prepend = true, value = Loggable.DEBUG)
	public void createProduct(final String productId, 
		final Integer quantity, final Double price) 
			throws ApplicationException {
		try {
			if (pRepo.findByProductId(productId) != null) {
				throw new Exception("Product:" + productId + " exists"); 
			}
			cg.sendAndWaitException(
					new CreateProductCell(
							new Product(productId, quantity, 
								new Price(price))), 
							aTIMEOUT, aUNIT);
		} catch (JSR303ViolationException e1) {
			throw e1;
		} catch (Exception e2) {
			throw new ApplicationException(e2);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.facade
	 * .FacadeCommandService#addAmountProduct(java.lang.String, 
	 * java.lang.Integer)
	 */
	@Override
	@Loggable(trim = false, prepend = true, value = Loggable.DEBUG)
	public void addAmountProduct(final String productId, 
		final Integer amountToAdd) 
			throws ApplicationException {
		try {
			throwExceptionIfProductNoFound(productId);
			cg.sendAndWaitException(
					new AddProductToProductCell(
							new Product(productId, amountToAdd, 
								new Price(0.0))),
							aTIMEOUT, aUNIT);
		} catch (JSR303ViolationException e1) {
			throw e1;
		} catch (DomainStateException e2) {
			throw e2;
		} catch (Exception e3) {
			throw new ApplicationException(e3);
		}
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.facade
	 * .FacadeCommandService#removeAmountProduct(java.lang.String, 
	 * java.lang.Integer)
	 */
	@Override
	@Loggable(trim = false, prepend = true, value = Loggable.DEBUG)
	public void removeAmountProduct(final String productId, 
		final Integer amountToRemove) 
			throws ApplicationException {
		try {
			throwExceptionIfProductNoFound(productId);
			cg.sendAndWaitException(
					new RemoveProductFromProductCell(
							new Product(productId, amountToRemove, 
								new Price(0.0))),
							aTIMEOUT, aUNIT);
		} catch (JSR303ViolationException e1) {
			throw e1;
		} catch (DomainStateException e2) {
			throw e2;
		} catch (Exception e3) {
			throw new ApplicationException(e3);
		}
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.facade
	 * .FacadeCommandService#changeCapacity(java.lang.String, 
	 * java.lang.Integer)
	 */
	@Override
	@Loggable(trim = false, prepend = true, value = Loggable.DEBUG)
	public void changeCapacity(final String productId, 
		final Integer newCapacity) 
			throws ApplicationException {
		try {
			throwExceptionIfProductNoFound(productId);
			cg.sendAndWaitException(
					new ChangeCapacityProductCell(
							new Product(productId, 0, 
								new Price(0.0)), newCapacity),
							aTIMEOUT, aUNIT);
		} catch (JSR303ViolationException e1) {
			throw e1;
		} catch (DomainStateException e2) {
			throw e2;
		} catch (Exception e3) {
			throw new ApplicationException(e3);
		}	

	}
	
	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.facade
	 * .FacadeCommandService#addProductToCustomerShoppingCart(java.lang.String, 
	 * java.lang.Integer, java.lang.String)
	 */
	@Override
	@Loggable(trim = false, prepend = true, value = Loggable.DEBUG)
	public void addProductToCustomerShoppingCart(
		final String productId, final Integer amountToGive, 
			final String customerId) throws ApplicationException {
		try {
			throwExceptionIfProductNoFound(productId);
			throwExceptionIfCustomerNoFound(customerId);
			cg.sendAndWaitException(
					new GiveProductToCustomer(
							new Product(productId, amountToGive, 
								new Price(0.0)), 
								customerId), aTIMEOUT, aUNIT);
		} catch (JSR303ViolationException e1) {
			throw e1;
		} catch (DomainStateException e2) {
			throw e2;
		} catch (Exception e3) {
			throw new ApplicationException(e3);
		}
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.facade
	 * .FacadeCommandService#removeProductFromCustomerShoppingCart(
	 * java.lang.String, java.lang.Integer, java.lang.String)
	 */
	@Override
	@Loggable(trim = false, prepend = true, value = Loggable.DEBUG)
	public void removeProductFromCustomerShoppingCart(
		final String productId, final Integer amountToRemove, 
			final String customerId) throws ApplicationException {
		try {
			throwExceptionIfProductNoFound(productId);
			throwExceptionIfCustomerNoFound(customerId);
			cg.sendAndWaitException(
					new ReturnProductFromCustomer(
							new Product(productId, amountToRemove, 
								new Price(0.0)), 
								customerId), aTIMEOUT, aUNIT);
		} catch (JSR303ViolationException e1) {
			throw e1;
		} catch (DomainStateException e2) {
			throw e2;
		} catch (Exception e3) {
			throw new ApplicationException(e3);
		}

	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.facade
	 * .FacadeCommandService#changeProductPrice(java.lang.String, 
	 * java.lang.Double)
	 */
	@Override
	@Loggable(trim = false, prepend = true, value = Loggable.DEBUG)
	public void changeProductPrice(final String productId, 
		final Double newPrice) 
			throws ApplicationException {
		try {
			throwExceptionIfProductNoFound(productId);
			cg.sendAndWaitException(
					new ChangePriceInProductCell(
							new Product(productId, 0, new Price(newPrice))),
							aTIMEOUT, aUNIT);
		} catch (JSR303ViolationException e1) {
			throw e1;
		} catch (DomainStateException e2) {
			throw e2;
		} catch (Exception e3) {
			throw new ApplicationException(e3);
		}	
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.facade
	 * .FacadeCommandService#createCustomer(java.lang.String)
	 */
	@Override
	@Loggable(trim = false, prepend = true, value = Loggable.DEBUG)
	public String createCustomer(final String address) 
		throws ApplicationException {
		String customerId = customerIdGen.getCustomerId();
		try {
			cg.sendAndWaitException(
					new CreateCustomer(customerId, new Address(address), 
						new Date()),
					aTIMEOUT, aUNIT);
		} catch (JSR303ViolationException e1) {
			throw e1;
		} catch (DomainStateException e2) {
			throw e2;
		} catch (Exception e3) {
			throw new ApplicationException(e3);
		}
		return customerId;
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.facade
	 * .FacadeCommandService#changeCustomerAddress(java.lang.String,
	 *  java.lang.String)
	 */
	@Override
	@Loggable(trim = false, prepend = true, value = Loggable.DEBUG)
	public void changeCustomerAddress(final String customerId, 
		final String newAddress) throws ApplicationException {
		try {
			throwExceptionIfCustomerNoFound(customerId);
			cg.sendAndWaitException(
					new ChangeCustomerAddress(customerId, 
						new Address(newAddress)),
					aTIMEOUT, aUNIT);
		} catch (JSR303ViolationException e1) {
			throw e1;
		} catch (DomainStateException e2) {
			throw e2;
		} catch (Exception e3) {
			throw new ApplicationException(e3);
		}
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.facade
	 * .FacadeCommandService#createOrderFromShoppingCartForCustomer(
	 * java.lang.String, java.lang.String)
	 */
	@Override
	@Loggable(trim = false, prepend = true, value = Loggable.DEBUG)
	public String createOrderFromShoppingCartForCustomer(
		final String customerId, 
		final String shipTo) throws ApplicationException {
		String orderId = orderIdGen.getOrderId();
		try {
			throwExceptionIfCustomerNoFound(customerId);
			cg.sendAndWaitException(
					new CreateOrderForCustomer(customerId, orderId, 
							new Date(), new Address(shipTo)), aTIMEOUT, aUNIT);
			cg.sendAndWaitException(new ClearShoppingCart(customerId), 
							aTIMEOUT, aUNIT);
		} catch (JSR303ViolationException e1) {
			throw e1;
		} catch (DomainStateException e2) {
			throw e2;
		} catch (Exception e3) {
			e3.printStackTrace();
			Logger.debug(this, "Exception:" + e3.getMessage());
			throw new ApplicationException(e3);
		}
		return orderId;
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.facade
	 * .FacadeCommandService#changeOrderStatus(
	 * java.lang.String, java.lang.Integer)
	 */
	@Override
	@Loggable(trim = false, prepend = true, value = Loggable.DEBUG)
	public void changeOrderStatus(final String orderId, 
		final Integer statusCode) throws ApplicationException {
		try {
			throwExceptionIfOrderNoFound(orderId);
			cg.sendAndWaitException(
					new ChangeOrderStatus(orderId, convertStatus(statusCode)),
					aTIMEOUT, aUNIT);
		} catch (JSR303ViolationException e1) {
			throw e1;
		} catch (DomainStateException e2) {
			throw e2;
		} catch (Exception e3) {
			throw new ApplicationException(e3);
		}

	}
	
	/**
	 * Convert status from out form code for inner domain code.
	 *
	 * @param statusCode the status code
	 * @return the history
	 */
	@Loggable(trim = false, prepend = false, value = Loggable.DEBUG)
	private History convertStatus(final Integer statusCode) {
		int code = statusCode.intValue();
		Date created = new Date();
		
		if (code == aSHIPPED) {
		    return new History(created, OrderStatus.SHIPPED);
		}
		if (code == aDELIVERED) {
		    return new History(created, OrderStatus.DELIVERED);
		}
		if (code == aCLOSED) {
		    return new History(created, OrderStatus.CLOSED);
		}
		throw new IllegalArgumentException("Code status:" + code 
				+ " not accepted must be " 
				+ aSHIPPED + "-SHIPPED "
				+ aDELIVERED + "-DELIVERED "
				+ aCLOSED + "-CLOSED");
	}
	
	/**
	 * Throw exception if product no found.
	 * Used for pre-validation before send command.
	 *
	 * @param productId the product id
	 * @throws Exception the exception
	 */
	private void throwExceptionIfProductNoFound(final String productId) 
			throws Exception {
		if (pRepo.findByProductId(productId) == null) {
		    throw new Exception("Product:" + productId + " no found");
		}
	}
	
	/**
	 * Throw exception if customer no found.
	 * Used for pre-validation before send command.
	 * 
	 * @param customerId the customer id
	 * @throws Exception the exception
	 */
	private void throwExceptionIfCustomerNoFound(final String customerId) 
			throws Exception {
		if (cRepo.findByCustomerId(customerId).isEmpty()) {
		    throw new Exception("Customer:" + customerId + " no found");
		}
	}
	
	/**
	 * Throw exception if order no found.
	 * Used for pre-validation before send command.
	 *
	 * @param orderId the order id
	 * @throws Exception the exception
	 */
	private void throwExceptionIfOrderNoFound(final String orderId) 
			throws Exception {
		if (oRepo.findByOrderId(orderId).isEmpty()) {
		    throw new Exception("Order:" + orderId + " no found");
		}
	}
}
