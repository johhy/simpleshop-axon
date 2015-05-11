package com.github.johhy.simpleshopaxon.core.api.shared;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.axonframework.commandhandling.interceptors.JSR303ViolationException;

import com.github.johhy.simpleshopaxon.core.api.exception.DomainStateException;

/**
 * The Interface ApplicationCommandGateway.
 * <p>
 * Main gateway for sending commands to application.
 * 
 * @author johhy
 */
public interface ApplicationCommandGateway {

	/**
	 * Send and wait exception.
	 *
	 * @param command the command
	 * @param timeout the timeout
	 * @param unit the unit
	 * @throws TimeoutException the timeout exception
	 * @throws InterruptedException the interrupted exception
	 * @throws DomainStateException the domain state exception
	 * @throws JSR303ViolationException the JSR303 violation exception
	 */
	void sendAndWaitException(Object command, long timeout, TimeUnit unit)
		throws TimeoutException, InterruptedException, DomainStateException, 
			JSR303ViolationException;
}
