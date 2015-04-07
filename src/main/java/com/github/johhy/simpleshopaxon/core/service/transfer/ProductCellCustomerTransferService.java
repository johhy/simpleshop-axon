package com.github.johhy.simpleshopaxon.core.service.transfer;

/**
 * The Interface ProductCellCustomerTransferService.
 * <p>
 * This is domain service to transfer product from 
 * ProductCell to Customer and vice versa.
 * <p>
 * For example ,service handle command "Transfer from ProductCell
 * to Customer" and send first command "Give Product to Customer"
 * to ProductCell, second command "AddProductForCustomer" to
 * Customer.If any exceptions throws while commands transfer service 
 * cancel commands(revert operations) and throws exception.
 * Parameters for commands takes from query model.
 * Methods in impl class must have @CommandHandler annotation.
 *
 * @author johhy
 */
interface ProductCellCustomerTransferService {

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
