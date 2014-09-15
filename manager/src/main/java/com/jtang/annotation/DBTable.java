/**
 * 
 */
package com.jtang.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Administartor
 *
 */
@Target(ElementType.TYPE) //applies to classes only!
@Retention(RetentionPolicy.RUNTIME)
public @interface DBTable {
	public String tableName();
	public String primaryKeyName();
}
