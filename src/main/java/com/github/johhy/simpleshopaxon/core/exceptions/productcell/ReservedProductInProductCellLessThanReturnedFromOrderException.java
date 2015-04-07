package com.github.johhy.simpleshopaxon.core.exceptions.productcell;

/**
 * @author johhy
 *
 */
public class ReservedProductInProductCellLessThanReturnedFromOrderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ReservedProductInProductCellLessThanReturnedFromOrderException(String msg) {
		super(msg);
	}

}
