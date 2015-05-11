package com.github.johhy.simpleshopaxon.web.rest.error;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolation;

import org.axonframework.commandhandling.interceptors.JSR303ViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.github.johhy.simpleshopaxon.core.api.exception.DomainStateException;
import com.github.johhy.simpleshopaxon.facade.ApplicationException;
import com.google.common.collect.Maps;

/**
 * The Class ControllerErrorHandler.
 * <p>
 * 
 * @author johhy
 */
@ControllerAdvice
public class ControllerErrorHandler {
	
	/**
	 * Handle request exception.
	 *
	 * @param ex the ex
	 * @return the map
	 */
	@ExceptionHandler({MissingServletRequestParameterException.class,
		UnsatisfiedServletRequestParameterException.class,
		HttpRequestMethodNotSupportedException.class,
		ServletRequestBindingException.class
	})
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Map<String, Object> handleRequestException(final Exception ex) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("error", "Request Error");
		map.put("cause", ex.getMessage());
		return map;
	}
	
	/**
	 * Handle domain state exceptions.
	 *
	 * @param dse the dse
	 * @return the map
	 */
	@ExceptionHandler(value = {DomainStateException.class})
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Map<String, Object> handleDomainStateExceptions(
			final DomainStateException dse) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("error", "Request Error");
		map.put("cause", dse.getMessage());
		return map;
	}
	
	/**
	 * Handle js r303 exceptions.
	 *
	 * @param jsrve the jsrve
	 * @return the map
	 */
	@ExceptionHandler(value = {JSR303ViolationException.class})
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Map<String, Object> handleJSR303Exceptions(
			final JSR303ViolationException jsrve) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("error", "Validation Failure");
        map.put("violations", convertConstraintViolation(jsrve));
        return map;

	}

	/**
	 * Handle illegal argument exceptions.
	 *
	 * @param iae the iae
	 * @return the map
	 */
	@ExceptionHandler(value = {IllegalArgumentException.class})
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Map<String, Object> handleIllegalArgumentExceptions(
			final IllegalArgumentException iae) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("error", "Request Error");
		map.put("cause", iae.getMessage());
		return map;
	}
	
	/**
	 * Convert constraint violation.
	 *
	 * @param ex the ex
	 * @return the list
	 */
	private List<ConstraintViolationErrorMessage> convertConstraintViolation(
				final JSR303ViolationException ex) {
            List<ConstraintViolationErrorMessage> result = 
                            new ArrayList<ConstraintViolationErrorMessage>();
             for (ConstraintViolation<Object> violation : ex.getViolations()) {
                     result.add(new ConstraintViolationErrorMessage(
                                     violation.getPropertyPath().toString(), 
                                     violation.getMessage()));
            }
            return result;
    }

	/**
	 * Handle application exceptions.
	 *
	 * @param ae the ae
	 */
	@ExceptionHandler(value = {ApplicationException.class})
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public void handleApplicationExceptions(final ApplicationException ae) {
	//	ae.printStackTrace();
		//no show exception for client
	}
}
