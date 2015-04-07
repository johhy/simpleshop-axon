package com.github.johhy.simpleshopaxon.core.service.transfer;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.johhy.simpleshopaxon.core.commandgateway.CustomerCommandGateway;
import com.github.johhy.simpleshopaxon.core.commandgateway.ProductCellCommandGateway;
import com.github.johhy.simpleshopaxon.core.commands.customer.AddProductToOrderCommand;
import com.github.johhy.simpleshopaxon.core.commands.customer.RemoveProductFromOrderCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.GiveAmountProductFromProductCellForOrderCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.ReturnAmountProductFromOrderToProductCellCommand;
import com.jcabi.aspects.Loggable;

@Service
public class ProductCellCustomerTransferServiceImpl implements
		ProductCellCustomerTransferService {
	
	private ProductCellCommandGateway productCellCG;
	
	private CustomerCommandGateway customerCG;
	
	public ProductCellCustomerTransferServiceImpl() {};
	
	@Autowired
	public ProductCellCustomerTransferServiceImpl(
			ProductCellCommandGateway productCellCG,
			CustomerCommandGateway customerCG) {
		super();
		this.productCellCG = productCellCG;
		this.customerCG = customerCG;
	}
	
	@Loggable(Loggable.DEBUG)
	@CommandHandler
	public void transferProductFromProductCellToCustomerCommand(
			TransferProductFromProductCellToCustomerCommand command)
			throws TransferServiceException {
		try {
			//first operation
			productCellCG.giveAmountProductFromProductCellForOrder(
					new GiveAmountProductFromProductCellForOrderCommand(
							command.getCodeOfProduct(), 
							command.getOrderId(),
							command.getAmount()));
			try {
				customerCG.addProductToOrder(new AddProductToOrderCommand(
						command.getCustomerId(),
						command.getOrderId(),
						command.getCodeOfProduct(),
						command.getAmount(),
						command.getPrice()));
			} catch (Exception e) {
				//rollback first operation
				productCellCG.returnAmountProductFromOrderToProductCell(
						new ReturnAmountProductFromOrderToProductCellCommand(
								command.getCodeOfProduct(),
								command.getOrderId(),
								command.getAmount()));
				throw e;
			}
		} catch (Exception e) {
			TransferServiceException ex =
					new TransferServiceException(
							"Exception transfer from ProductCell "
							+ "to Customer for command:" + command);
			ex.initCause(e);
			throw ex;
		}
	}
	
	@Loggable(Loggable.DEBUG)
	@CommandHandler
	public void transferProductFromCustomerToProductCellCommand(
			TransferProductFromCustomerToProductCellCommand command)
			throws TransferServiceException {
		try {
			//first operation
			customerCG.removeProductFromOrder(
					new RemoveProductFromOrderCommand(
							command.getCustomerId(),
							command.getOrderId(),
							command.getCodeOfProduct(),
							command.getAmount()));
			try {
				productCellCG.returnAmountProductFromOrderToProductCell(
						new ReturnAmountProductFromOrderToProductCellCommand(
								command.getCodeOfProduct(),
								command.getOrderId(),
								command.getAmount()));
			} catch (Exception e) {
				//rollback first operation
				customerCG.addProductToOrder(
						new AddProductToOrderCommand(
								command.getCustomerId(),
								command.getOrderId(),
								command.getCodeOfProduct(),
								command.getAmount(),
								command.getPrice()));
				throw e;
			}
		} catch (Exception e) {
			TransferServiceException ex =
					new TransferServiceException(
							"Exception transfer from Customer "
							+ "to ProductCell for command:" + command);
			ex.initCause(e);
			throw ex;
		}

	}

}
