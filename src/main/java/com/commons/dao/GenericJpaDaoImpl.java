/**
 * 
 */
package com.commons.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import com.commons.interfaces.GenericJpaDao;

/**
 * @author gonzalo.tamos
 *
 */

@Repository
public abstract class GenericJpaDaoImpl<T, PK extends Serializable> implements GenericJpaDao<T, PK>{

	private static final long serialVersionUID = 5470474603186009274L;

	public final static Logger _logger = org.slf4j.LoggerFactory.getLogger(GenericJpaDaoImpl.class);

	protected Class<T> entityClass;


	@PersistenceContext
	protected EntityManager entityManager;	

	// Constructors
	@SuppressWarnings("unchecked")
	public GenericJpaDaoImpl() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
	}		

	@Override
	public void update(Object entity) {
		_logger.debug("Updating entity = " + entity);
		this.entityManager.merge(entity);
	}

	@Override
	public void saveOrUpdate(T entity) {
		try{
			_logger.trace("Saving entity = " + entity);

			this.entityManager.persist(entity);
		}catch(Exception e){
			_logger.trace("Exception happenned, mergin entity");
			this.entityManager.merge(entity);
		}
	}

	@Override
	public void delete(Object entity) {
		_logger.trace(">");
		this.entityManager.remove(entity);
	}
	
	@Override
	public void deleteById(PK id) {
		this.delete(this.findById(id));
	}

	@Override
	public T findById(PK id) {
		return this.entityManager.find(entityClass, id);
	}

	@Override
	public List<T> findByProperty(String propertyName, Object propertyValue) {

		Map<String, Object> properties = new HashMap<String, Object>();
		
		properties.put(propertyName, propertyValue);
		
		return findByProperties(properties);
	}

	@Override
	public List<T> findByProperties(Map<String, Object> properties){

		CriteriaBuilder	criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<T> entity = criteriaQuery.from(entityClass);

		List<Predicate> predicates = new ArrayList<Predicate>();

		properties.forEach((k,v)->{
			predicates.add(criteriaBuilder.equal(entity.get(k), v));
		});

		criteriaQuery.select(entity).where(predicates.toArray(new Predicate[]{}));

		return entityManager.createQuery(criteriaQuery).getResultList();
	}
	
	@Override
	public T getByProperties(Map<String, Object> properties){

		try {
			return findByProperties(properties).get(0);
		}catch(IndexOutOfBoundsException e){
			return null;
		}
	}

	@Override
	public T getByProperty(String propertyName, Object propertyValue){
		try {
			return findByProperty(propertyName, propertyValue).get(0);
		}catch(IndexOutOfBoundsException e){
			return null;
		}
	}
	
	@Override
	public List<T> findAll() {
		
		CriteriaBuilder	criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<T> entity = criteriaQuery.from(entityClass);
		criteriaQuery.select(entity);
		return entityManager.createQuery(criteriaQuery).getResultList();
	}
}
