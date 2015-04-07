package com.github.johhy.simpleshopaxon.core.commandgateway;

import com.github.johhy.simpleshopaxon.core.service.transfer.TransferProductFromCustomerToProductCellCommand;
import com.github.johhy.simpleshopaxon.core.service.transfer.TransferProductFromProductCellToCustomerCommand;
import com.github.johhy.simpleshopaxon.core.service.transfer.TransferServiceException;

/**
 * The Interface ProductCellCustomerTransferServiceCommandGateway.
 *
 * Command gateway for ProductCellCustomerTransferService. 
 * <p>
 * Used for send commands to transfer product from ProductCell to
 * Customer and vice versa.
 * No values return but exceptions.
 * For define command parameters use query model.
 *  
 * @author johhy
 *
 */
public interface ProductCellCustomerTransferServiceCommandGateway {

	/**
	 * Transfer product from product cell to customer command.
	 *
	 * @param command the command
	 * @throws TransferServiceException the transfer service exception
	 */
	void transferProductFromProductCellToCustomerCommand(
			TransferProductFromProductCellToCustomerCommand command)
					throws TransferServiceException;
	
	/**
	 * Transfer product from customer to product cell command.
	 *
	 * @param command the command
	 * @throws TransferServiceException the transfer service exception
	 */
	void transferProductFromCustomerToProductCellCommand(
			TransferProductFromCustomerToProductCellCommand command) 
					throws TransferServiceException;
}
