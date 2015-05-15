package com.github.johhy.simpleshopaxon.core.infra;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.springframework.data.repository.CrudRepository;

/**
 * The Interface Find.
 * <p>
 * 
 * Needs for pre-validation 
 * check for exists object before
 * send command.
 * Query defined query database with
 * method.
 * Validate field must be String.
 * Repository must extends <link>CrudRepository</link>.
 * 
 * @author johhy
 * 
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FindValidator.class)
public @interface Find {
    
    /**
     * Message.
     *
     * @return the string
     */
    String message() default "";
	 
    /**
     * Groups.
     *
     * @return the class[]
     */
    Class<?> [ ] groups() default {};
    
    /**
     * Payload.
     *
     * @return the class<? extends payload>[]
     */
    Class<? extends Payload>[] payload() default {};
    
    /**
     * Repository where find object.
     *
     * @return the class<? extends crud repository>
     */
    @SuppressWarnings("rawtypes")
    Class< ? extends CrudRepository> repository();
    
    /**
     * Method name to find object.
     *
     * @return the string
     */
    String methodName();
    
    /**
     * Must exists, true if object must exists.
     *
     * @return true, if successful
     */
    boolean mustExists() default true;
}
