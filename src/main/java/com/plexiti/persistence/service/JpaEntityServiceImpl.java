package com.plexiti.persistence.service;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.plexiti.helper.Objects;

public abstract class JpaEntityServiceImpl<E> implements EntityService<E>, Serializable {

	private static final long serialVersionUID = -3067290117110657534L;
	protected Logger log = Logger.getLogger(getClass().getName());

	private Class<E> entityClass;

	@PersistenceContext
	protected EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public JpaEntityServiceImpl () {
		entityClass = (Class<E>) Objects.getActualTypeArgument(getClass(), 0);
	}

	@Override
	public E findOrNew (Object id) {
		return (id == null) ? newInstance () : find(id);
	}

	@Override
	public E newInstance () {
		return Objects.newInstance(entityClass);
	}

	@Override
	public E find (Object id) {
		return entityManager.find(entityClass, id);		
	}

	@Override
	public void persist(E entity) {
		entityManager.persist(entity);
	}
	
	@Override
	public void merge(E entity) {
		entityManager.merge(entity);
	}

	@Override
	public void delete(E entity) {
		entityManager.remove(entity);
	}

	@Override
	public void persistAndFlush(E entity) {
		if (entityManager.contains(entity)) {
			persist(entity);
		} else {
			persist(entity);
			entityManager.flush();
		}
	}
	
	protected Class<E> getEntityClass() {
		return entityClass;
	}
	
}
