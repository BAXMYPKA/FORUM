package ru.shop.forum.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shop.forum.entities.AbstractEntity;
import ru.shop.forum.repositories.EntityRepository;

@Service
public abstract class EntityService<T extends AbstractEntity> {
	
	@Autowired
	protected EntityRepository<T, Long> repository;
	
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
