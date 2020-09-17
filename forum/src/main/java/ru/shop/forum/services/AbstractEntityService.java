package ru.shop.forum.services;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.shop.forum.entities.AbstractEntity;
import ru.shop.forum.repositories.EntityRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Service
public abstract class AbstractEntityService<T extends AbstractEntity, S extends EntityRepository<T, Long>> {
	
	protected S repository;
	
	/**
	 * @param repository If a special {@link EntityRepository} for a subclass is needed it must be included here.
	 *                   Otherwise if no specific repository is created just the typed {@link EntityRepository} will be enough here
	 */
	protected abstract void setRepository(S repository);
	
	public Optional<T> findOne(Long id) {
		return repository.findById(id);
	}
	
	public Optional<T> findOne(T example) {
		return repository.findOne(Example.of(example));
	}
	
	public Collection<T> findAllByIds(Iterable<Long> ids) {
		return repository.findAllById(Objects.requireNonNullElse(ids, Collections.singleton(0L)));
	}
	
	public Page<T> findAll(Pageable pageable) {
		return repository.findAll(pageable);
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
