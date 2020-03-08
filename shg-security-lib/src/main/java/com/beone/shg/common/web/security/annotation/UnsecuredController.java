package com.beone.shg.common.web.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Unsecured is an annotation used to mark controller as unsecured, to acknowledge that access to this method is 
 * purposefully not secured by SHG Security.
 *           
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UnsecuredController {
	public String justification();
}
