/**
 * 
 */
package com.commons.utils;

import java.util.Collection;

/**
 * @author gonzalo.tamos
 *
 */
public class CollectionUtils {

	public static boolean isNotEmpty(Collection<?> collection){
		
		return collection != null && !collection.isEmpty();
	}
	
	public static boolean isEmpty(Collection<?> collection){
		return !isNotEmpty(collection);
	}
}
