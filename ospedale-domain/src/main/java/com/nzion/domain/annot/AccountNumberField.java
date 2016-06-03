package com.nzion.domain.annot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Sandeep Prusty
 * May 11, 2010
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AccountNumberField {	
	public abstract String value() default "accountNumber";
}