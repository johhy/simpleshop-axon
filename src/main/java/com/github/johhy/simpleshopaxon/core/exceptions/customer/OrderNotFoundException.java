package com.github.johhy.simpleshopaxon.core.exceptions.customer;

/**
 * @author johhy
 *
 */
public class OrderNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OrderNotFoundException(String msg) {
		super(msg);
	}
}
