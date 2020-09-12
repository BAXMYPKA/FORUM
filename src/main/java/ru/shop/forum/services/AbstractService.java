package ru.shop.forum.services;

import org.springframework.stereotype.Service;
import ru.shop.forum.entities.AbstractEntity;
import ru.shop.forum.repositories.EntityRepository;

@Service
public abstract class AbstractService<T extends AbstractEntity, S extends EntityRepository<T, Long>> {
	
	protected S repository;
	
	/**
	 * @param repository If a special {@link EntityRepository} for a subclass is needed it must be included here.
	 *                   Otherwise if no specific repository is created just the typed {@link EntityRepository} will be enough here
	 */
	protected abstract void setRepository(S repository);
	
	protected T getOne(Long id) {
		return repository.getOne(id);
	}
	
	protected void delete(T t) {
		if (t == null) return;
		repository.delete(t);
	}
	
	protected void deleteById(Long id) {
		if (id == null) return;
		repository.deleteById(id);
	}
	
	protected void deleteAll() {
		repository.deleteAll();
	}
	
	protected void deleteAll(Iterable<T> entities) {
		if (entities == null) return;
		repository.deleteAll(entities);
	}
	
	protected Long count() {
		return repository.count();
	}
	
	protected Boolean existById(Long id) {
		if (id == null) return false;
		return repository.existsById(id);
	}
}
