package com.github.johhy.simpleshopaxon.core.api.shared;

import org.hibernate.validator.constraints.NotBlank;

/**
 * The Class Address.
 * <p>
 * Domain object.Value object.
 * Keeps address.
 * 
 * @author johhy
 */
public class Address {
	
	/** The address. */
	@NotBlank
	private final String address;

	/**
	 * Instantiates a new address.
	 *
	 * @param createdAddress the created address
	 */
	public Address(final String createdAddress) {
		super();
		this.address = createdAddress;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public final String getAddress() {
		return address;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    int hash = 0;
	    if (address != null) {
		hash = address.hashCode();
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
	    if (!(obj instanceof Address)) {
		return false;
	    }
	    Address other = (Address) obj;
	    if (address == null) {
		if (other.address != null) {
		    return false;
		}
	    } else if (!address.equals(other.address)) {
		return false;
	    }
	    return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		return "Address [address=" + address + "]";
	}
	
	

}
