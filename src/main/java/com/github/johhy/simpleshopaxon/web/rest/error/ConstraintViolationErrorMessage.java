package com.github.johhy.simpleshopaxon.web.rest.error;

/**
 * The Class ConstraintViolationErrorMessage.
 * <p>
 * 
 * @author johhy
 */
public final class ConstraintViolationErrorMessage {
	
	/** The field. */
	private String field;
	
	/** The message. */
	private String message;

	
	/**
	 * Instantiates a new constraint violation error message.
	 *
	 * @param newField the new field
	 * @param newMassage the new massage
	 */
	public ConstraintViolationErrorMessage(final String newField, 
		final String newMassage) {
		super();
		this.field = newField;
		this.message = newMassage;
	}

	/**
	 * Gets the field.
	 *
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * Sets the field.
	 *
	 * @param newField the new field
	 */
	public void setField(final String newField) {
		this.field = newField;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param newMessage the new message
	 */
	public void setMessage(final String newMessage) {
		this.message = newMessage;
	}
	
	

}
