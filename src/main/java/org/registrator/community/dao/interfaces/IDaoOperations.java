package org.registrator.community.dao.interfaces;

import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceType;

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

    public List<T> getAllByResourceId(Resource resource);

    public List<T> getAllByResourceTypeId(ResourceType resourceType);

    public int isEmpty();
}
