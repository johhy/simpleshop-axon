
package com.github.johhy.simpleshopaxon.facade;


/**
 * The Class ApplicationException.
 * <p>
 * Class used to throw application exceptions. 
 * Application exception created from exceptions 
 * generated domains, infrastructure or others beside 
 * facade.
 * 
 * @author johhy
 */
public class ApplicationException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new application exception.
	 *
	 * @param message the message
	 */
	public ApplicationException(final String message) {
		super(message);
	}

	/**
	 * Instantiates a new application exception.
	 *
	 * @param cause the cause
	 */
	public ApplicationException(final Throwable cause) {
	    super(cause);
	}

}
