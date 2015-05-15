package com.github.johhy.simpleshopaxon.core.infra;

import java.lang.reflect.Method;
import java.util.Collection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;

/**
 * The Class FindValidator.
 * <p>
 * 
 * Validator for @see com.github.johhy.simpleshopaxon.core
 * .infra.Find annotation.
 * 
 * @author johhy
 * 
 */
public class FindValidator implements ConstraintValidator<Find, String> {

    /** The application context needs to get repository. */
    @Autowired
    private ApplicationContext ac;

    /** The repository where seek object. */
    private Object repository;
    
    /** The method name to invoke. */
    private String methodName;
    
    /** The must exists, parameter to validate logic.
     *  If this parameter true and object exists - ok
     *  else error.
     *  If this parameter false and object no exists -ok
     *  else error.
     */
    private boolean mustExists;
    
    /** The Constant NOT_FOUND. */
    private final String NOT_FOUND = " not found";
    
    /** The Constant EXISTS. */
    private static final String EXISTS = " exists";
    
    /* (non-Javadoc)
     * @see javax.validation.ConstraintValidator#initialize(
     * java.lang.annotation.Annotation)
     */
    @Override
    public final void initialize(final Find constraintAnnotation) {
	this.methodName = constraintAnnotation.methodName();
	this.mustExists = constraintAnnotation.mustExists();
	this.repository = ac.getBean(constraintAnnotation.repository());
	
    }

    /* (non-Javadoc)
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
     */
    @Override
    public final boolean isValid(final String value, final ConstraintValidatorContext context) {
	Method method = null;
	method = ReflectionUtils
		    	.findMethod(repository.getClass(), methodName, 
		    		String.class);
	if (method != null) {
	    Object result = ReflectionUtils
			.invokeMethod(method, repository, value);
	    context.disableDefaultConstraintViolation();
	    
	    //convert result to null if collection is empty
	    if (result instanceof Collection<?>) {
		Collection<?> collectionResult = (Collection<?>)result;
		if (collectionResult.isEmpty()) {
		    result = null;
		}
	    }
	    
	    if (mustExists == true) {
		if (result != null) {
		    return true;
		} else {
		    context.buildConstraintViolationWithTemplate(
			value + NOT_FOUND).addConstraintViolation();
		}
	    } else {
		if (result == null) {
		    return true;
		} else {
		    context.buildConstraintViolationWithTemplate(
			value + EXISTS).addConstraintViolation();
		}
	    }
	    
	} else {
	    throw new IllegalArgumentException("Method " 
	    	    			+ methodName + NOT_FOUND);
	}
	return false;
    }
}
