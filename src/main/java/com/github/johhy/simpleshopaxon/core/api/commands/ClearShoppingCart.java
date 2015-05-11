package com.github.johhy.simpleshopaxon.core.api.commands;

/**
 * The Class ClearShoppingCart.
 * 
 * @author johhy
 */
public class ClearShoppingCart extends CustomerCommand {

	/**
	 * Instantiates a new clear shopping cart.
	 *
	 * @param customerId the customer id
	 */
	public ClearShoppingCart(final String customerId) {
		super(customerId);
	}

	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.api.commands
	 * .CustomerCommand#toString()
	 */
	@Override
	public final String toString() {
		return "ClearShoppingCart [customerId=" + getCustomerId() + "]";
	}

}
