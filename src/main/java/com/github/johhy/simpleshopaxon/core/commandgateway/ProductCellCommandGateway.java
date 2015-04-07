package com.github.johhy.simpleshopaxon.core.commandgateway;


import com.github.johhy.simpleshopaxon.core.commands.productcell.AddProductToProductCellCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.ChangePriceOfProductCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.CreateProductCellCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.GiveAmountProductFromProductCellForOrderCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.RemoveProductCellCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.RemoveProductFromProductCellCommand;
import com.github.johhy.simpleshopaxon.core.commands.productcell.ReturnAmountProductFromOrderToProductCellCommand;
import com.github.johhy.simpleshopaxon.core.exceptions.customer.OrderNotFoundException;
import com.github.johhy.simpleshopaxon.core.exceptions.productcell.ProductInProductCellLessThanNeedException;
import com.github.johhy.simpleshopaxon.core.exceptions.productcell.RemoveProductLessThanNeedException;
import com.github.johhy.simpleshopaxon.core.exceptions.productcell.ReservedProductInProductCellLessThanReturnedFromOrderException;
import com.github.johhy.simpleshopaxon.core.exceptions.productcell.ReservedProductsExistsInProductCellException;

/**
 * The Interface ProductCellCommandGateway
 * 
 * Command gateway for product cell
 * <p>
 * Used for send command for product cell,
 * no values return but exceptions.
 * Note that repository throws AggregateNotFoundException
 * when not found object.
 *  
 *  @author johhy
 */
public interface ProductCellCommandGateway {
	
	void createProductCell(CreateProductCellCommand command);
	
	void addAmountProductToProductCell(AddProductToProductCellCommand command);

	void removeAmountProductFromProductCell(RemoveProductFromProductCellCommand command) 
		throws RemoveProductLessThanNeedException;
	
	void changePriceOfProductInProductCell(ChangePriceOfProductCommand command);
	
	void giveAmountProductFromProductCellForOrder(GiveAmountProductFromProductCellForOrderCommand command)
		throws ProductInProductCellLessThanNeedException;
	
	void returnAmountProductFromOrderToProductCell(ReturnAmountProductFromOrderToProductCellCommand command)
		throws OrderNotFoundException,ReservedProductInProductCellLessThanReturnedFromOrderException;
	
	void removeProductCell(RemoveProductCellCommand command)
		throws ReservedProductsExistsInProductCellException;
}
