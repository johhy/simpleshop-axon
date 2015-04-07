package com.github.johhy.simpleshopaxon.core.commandgateway;


import com.github.johhy.simpleshopaxon.core.commands.customer.AddProductToOrderCommand;
import com.github.johhy.simpleshopaxon.core.commands.customer.ChangeCustomerInfoCommand;
import com.github.johhy.simpleshopaxon.core.commands.customer.CreateCustomerCommand;
import com.github.johhy.simpleshopaxon.core.commands.customer.CreateOrderCommand;
import com.github.johhy.simpleshopaxon.core.commands.customer.DeleteOrderCommand;
import com.github.johhy.simpleshopaxon.core.commands.customer.RemoveCustomerCommand;
import com.github.johhy.simpleshopaxon.core.commands.customer.RemoveProductFromOrderCommand;
import com.github.johhy.simpleshopaxon.core.exceptions.customer.AmountProductToRemoveNotEqualInOrderException;
import com.github.johhy.simpleshopaxon.core.exceptions.customer.OrderNotEmptyException;
import com.github.johhy.simpleshopaxon.core.exceptions.customer.OrderNotFoundException;
import com.github.johhy.simpleshopaxon.core.exceptions.customer.ProductExistsException;
import com.github.johhy.simpleshopaxon.core.exceptions.customer.ProductNotFoundException;

/**
 * The Interface CustomerCommandGateway
 * 
 * Command gateway for customer
 * <p>
 * Used for send command for customer,
 * no values return but exceptions.
 * Note that repository throws AggregateNotFoundException
 * when not found object.
 * 
 * @author johhy
 */
public interface CustomerCommandGateway {
	
	void createCustomer(CreateCustomerCommand command);
	
	void removeCustomer(RemoveCustomerCommand command);
	
	void createOrder(CreateOrderCommand command); 

	void deleteOrder(DeleteOrderCommand command)
			throws OrderNotFoundException,OrderNotEmptyException;
	
	void addProductToOrder(AddProductToOrderCommand command)
			throws OrderNotFoundException,ProductExistsException;
	
	void removeProductFromOrder(RemoveProductFromOrderCommand command) 
			throws ProductNotFoundException,OrderNotFoundException,
			AmountProductToRemoveNotEqualInOrderException;
	
	void changeCustomerInfo(ChangeCustomerInfoCommand command);
	
}
