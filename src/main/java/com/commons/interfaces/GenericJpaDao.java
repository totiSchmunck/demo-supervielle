/**
 * 
 */
package com.commons.interfaces;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author gonzalo.tamos
 *
 */

public interface GenericJpaDao<T, PK extends Serializable> extends Serializable {
	
	void update(T entity);

	void saveOrUpdate(T entity);

	void delete(T entity);

	void deleteById(PK id);

	T findById(PK id);

	List<T> findByProperties(Map<String, Object> properties);
	
	List<T> findByProperty(String propertyName, Object propertyValue);

	T getByProperty(String propertyName, Object propertyValue);
	
	T getByProperties(Map<String, Object> properties);

	List<T> findAll();

}
