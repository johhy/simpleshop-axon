package com.github.johhy.simpleshopaxon.core.domain.productcell;

import org.axonframework.commandhandling.CommandHandlerInterceptor;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.InterceptorChain;
import org.axonframework.unitofwork.UnitOfWork;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.johhy.simpleshopaxon.core.commands.productcell.AbstractProductCellCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.CreateProductCellCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.GiveAmountProductFromProductCellForOrderCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.RemoveProductCellCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.RemoveProductFromProductCellCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.ReturnAmountProductFromOrderToProductCellCommand;
import com.github.johhy.simpleshopaxon.core.exceptions.customer.OrderNotFoundException;
import com.github.johhy.simpleshopaxon.core.exceptions.productcell.ProductCellExistsException;
import com.github.johhy.simpleshopaxon.core.exceptions.productcell.ProductCellNoFoundException;
import com.github.johhy.simpleshopaxon.core.exceptions.productcell.ProductInProductCellLessThanNeedException;
import com.github.johhy.simpleshopaxon.core.exceptions.productcell.RemoveProductLessThanNeedException;
import com.github.johhy.simpleshopaxon.core.exceptions.productcell.ReservedProductInProductCellLessThanReturnedFromOrderException;
import com.github.johhy.simpleshopaxon.core.exceptions.productcell.ReservedProductsExistsInProductCellException;
import com.github.johhy.simpleshopaxon.query.productcell.ProductCellRepository;
import com.github.johhy.simpleshopaxon.query.productcell.ProductCellTable;
import com.github.johhy.simpleshopaxon.query.productcell.ReservedProductRepository;
import com.github.johhy.simpleshopaxon.query.productcell.ReservedProductTable;
import com.jcabi.aspects.Loggable;

/**
 * The Class ProductCellCommandsValidator.
 * This class validate commands send to ProductCell
 * aggregate and throw Exceptions if any states and
 * command not acceptable.
 * 
 * To find parameters for validation is used repository
 * query part.
 *  
 *  @author johhy
 */
public class ProductCellCommandsValidator implements CommandHandlerInterceptor {

	/** The reserved product repository. */
	private ReservedProductRepository reservedProductRepository;
	
	/** The product cell repository. */
	private ProductCellRepository productCellRepository;
	
	/**
	 * Sets the reserved product repository.
	 *
	 * @param reservedProductRepository the new reserved product repository
	 */
	@Autowired
	public void setReservedProductRepository(ReservedProductRepository reservedProductRepository) {
		this.reservedProductRepository = reservedProductRepository;
	}
	
	/**
	 * Sets the product cell repository.
	 *
	 * @param productCellRepository the new product cell repository
	 */
	@Autowired
	public void setProductCellRepository(ProductCellRepository productCellRepository) {
		this.productCellRepository = productCellRepository;
	}

	/* (non-Javadoc)
	 * @see org.axonframework.commandhandling.CommandHandlerInterceptor#handle(org.axonframework.commandhandling.CommandMessage, org.axonframework.unitofwork.UnitOfWork, org.axonframework.commandhandling.InterceptorChain)
	 */
	@Loggable(Loggable.DEBUG)
	public Object handle(CommandMessage<?> commandMessage, UnitOfWork unitOfWork, InterceptorChain interceptorChain)
			throws Throwable {
		Object command = commandMessage.getPayload();
		//validate product no found
		if(command instanceof AbstractProductCellCommand) {
			AbstractProductCellCommand rc =
					(AbstractProductCellCommand) command;
			if(productCellRepository
					.findByCodeOfProduct(rc.getCodeOfProduct())==null) {
				throw new ProductCellNoFoundException("Product cell " +
					rc.getCodeOfProduct() + " no found");
			};
		}
		//validate Order no found and Reserved product less than returned
		if(command instanceof ReturnAmountProductFromOrderToProductCellCommand) {
			ReturnAmountProductFromOrderToProductCellCommand rc =
					(ReturnAmountProductFromOrderToProductCellCommand)command;
			ReservedProductTable t =reservedProductRepository
				.findByOrderIdAndCodeOfProduct(rc.getOrderId(), rc.getCodeOfProduct());
			if(t==null) {
				throw new OrderNotFoundException("Order " + rc.getOrderId() + 
						"for product " + rc.getCodeOfProduct() + " no found");
			} else if(t.getAmount()<rc.getAmount()) {
				throw new ReservedProductInProductCellLessThanReturnedFromOrderException("Reserved product " +
						rc.getCodeOfProduct() + " is " + t.getAmount() + " req " + rc.getAmount());
			}
		//validate reserved product exists
		} else if(command instanceof RemoveProductCellCommand) {
			RemoveProductCellCommand rc = (RemoveProductCellCommand) command;
			if(!reservedProductRepository
					.findByCodeOfProduct(rc.getCodeOfProduct()).isEmpty()) {
				throw new ReservedProductsExistsInProductCellException("Product " +
					rc.getCodeOfProduct() + " reserved.No can delete ProductCell");
			};
		//validate is product less than need remove
		} else if(command instanceof RemoveProductFromProductCellCommand) {
			RemoveProductFromProductCellCommand rc = 
					(RemoveProductFromProductCellCommand)command;
			ProductCellTable t = productCellRepository
					.findByCodeOfProduct(rc.getCodeOfProduct());
			if(t.getQuantity()<rc.getQuantity()) {
				throw new RemoveProductLessThanNeedException("Quantity req:" + rc.getQuantity() +
						" in product cell is:" + t.getQuantity());
			}
		//validate is product less than need to give 
		} else if(command instanceof GiveAmountProductFromProductCellForOrderCommand) {
			GiveAmountProductFromProductCellForOrderCommand rc =
					(GiveAmountProductFromProductCellForOrderCommand)command;
			ProductCellTable t = productCellRepository
					.findByCodeOfProduct(rc.getCodeOfProduct());
			if(t.getQuantity()<rc.getAmount()) {
				throw new ProductInProductCellLessThanNeedException("Quantity req:" + 
						rc.getAmount() +" in product cell is:" + t.getQuantity());
			}
		} else if(command instanceof CreateProductCellCommand) {
			CreateProductCellCommand rc =
					(CreateProductCellCommand) command;
			if(productCellRepository
					.findByCodeOfProduct(rc.getCodeOfProduct())!=null) {
				throw new ProductCellExistsException("Product cell " + 
					rc.getCodeOfProduct() + " exists");
			}
		}
		return interceptorChain.proceed(commandMessage);
	}
	

}
