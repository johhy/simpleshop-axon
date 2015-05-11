package com.github.johhy.simpleshopaxon.core.api.shared;

import javax.validation.constraints.Min;


/**
 * The Class Price.
 * <p>
 * Domain object.Value object.
 * Keeps price.
 * 
 * @author johhy
 */
public class Price {
	
	/** The value. */
	@Min(0)
	private final Double value;

	/**
	 * Instantiates a new price.
	 *
	 * @param createdPriceValue the created price value
	 */
	public Price(final Double createdPriceValue) {
		super();
		this.value = createdPriceValue;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public final Double getValue() {
		return value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		int hash = 0;
		if (value != null) {
		    hash = value.hashCode();
		}
		result = prime * result + hash;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals(final Object obj) {
		if (this == obj) {
		    return true;
		}
		if (obj == null) {
		    return false;
		}
		if (!(obj instanceof Price)) {
		    return false;
		}
		Price other = (Price) obj;
		if (value == null) {
			if (other.value != null) {
			    return false;
			}
		} else if (!value.equals(other.value)) {
		    return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		return "Price [value=" + value + "]";
	}

}
