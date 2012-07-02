package com.plexiti.persistence.service;


public interface EntityService<E> {

	E newInstance ();
	E find (Object id);	
	E findOrNew (Object id);
	void persist(E entity);
	void persistAndFlush(E entity);
	void merge(E entity);
	void delete(E entity);

}
