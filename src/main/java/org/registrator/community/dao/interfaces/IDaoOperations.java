package org.registrator.community.dao.interfaces;

import java.util.List;

public interface IDaoOperations<T> {

	public void add(T entity);

	public void update(T entity);

	public T findById(Integer entityId);

	public T findByLogin(String entityLogin);

	public void delete(T entity);

	public List<T> getAll();

	public void deleteAll();



public T findByIdentifier(String entityIdentifier);		

}
