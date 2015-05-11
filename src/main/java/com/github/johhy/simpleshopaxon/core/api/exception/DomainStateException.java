package com.github.johhy.simpleshopaxon.core.api.exception;

/**
 * The Class DomainStateException.
 * <p>
 * This class used for throws domain illegal state when
 * domain rules is violated.
 * 
 * @author johhy
 */
public class DomainStateException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;


	/**
	 * Instantiates a new domain state exception.
	 *
	 * @param message the message
	 */
	public DomainStateException(final String message) {
		super(message);
	}

}
