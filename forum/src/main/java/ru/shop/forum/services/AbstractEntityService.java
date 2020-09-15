package ru.shop.forum.services;

import org.springframework.stereotype.Service;
import ru.shop.forum.entities.AbstractEntity;
import ru.shop.forum.entities.AbstractForumEntity;
import ru.shop.forum.repositories.EntityRepository;

@Service
public abstract class AbstractEntityService<T extends AbstractEntity, S extends EntityRepository<T, Long>> {
	
	protected S repository;
	
	/**
	 * @param repository If a special {@link EntityRepository} for a subclass is needed it must be included here.
	 *                   Otherwise if no specific repository is created just the typed {@link EntityRepository} will be enough here
	 */
	protected abstract void setRepository(S repository);
	
	public T getOne(Long id) {
		return repository.getOne(id);
	}
	
	public void delete(T t) {
		if (t == null) return;
		repository.delete(t);
	}
	
	public void deleteById(Long id) {
		if (id == null) return;
		repository.deleteById(id);
	}
	
	public void deleteAll() {
		repository.deleteAll();
	}
	
	public void deleteAll(Iterable<T> entities) {
		if (entities == null) return;
		repository.deleteAll(entities);
	}
	
	public Long count() {
		return repository.count();
	}
	
	public Boolean existById(Long id) {
		if (id == null) return false;
		return repository.existsById(id);
	}
}
