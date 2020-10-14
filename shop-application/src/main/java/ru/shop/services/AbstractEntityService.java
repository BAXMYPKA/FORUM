package ru.shop.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.shop.entities.AbstractEntity;
import ru.shop.repositories.EntityRepository;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

//@NoArgsConstructor
@Getter
@Setter
@Service
public abstract class AbstractEntityService<T extends AbstractEntity, R extends EntityRepository<T>> {
	
	@Getter(AccessLevel.PROTECTED)
	protected R repository;
	
	@Autowired
	protected ModelMapper modelMapper;
	
	/**
	 * MUST be set in every subclass constructor as {@code this.entityClass = Entity<T>.class}
	 */
	@NonNull
	protected Class<T> entityClass;
	
	@Value("${spring.data.web.pageable.max-page-size}")
	private Integer MAX_ENTITIES_AT_ONCE;
	
	@Autowired
	public AbstractEntityService(R repository) {
		this.repository = repository;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Optional<T> findOne(Long id) {
		return repository.findById(id);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Optional<T> findOne( T entityExample) {
		return repository.findOne(Example.of(entityExample));
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Page<T> findAllByIds(Pageable pageable, Set<Long> ids) throws IllegalArgumentException {
		if (ids == null) throw new IllegalArgumentException("A collection of id's cannot be null!");
		if (ids.size() > MAX_ENTITIES_AT_ONCE) {
			ids = ids.stream().limit(50).collect(Collectors.toSet());
		}
		//TODO: to create pageable response based on pageable info
		
		List<T> allByIds = repository.findAllById(Objects.requireNonNullElse(ids, Collections.singleton(0L)));
		return new PageImpl<T>(allByIds, pageable, allByIds.size());
	}
	
	/**
	 * {@link Pageable} URL parameters:
	 * .param("page", "5")
	 * .param("size", "10")
	 * .param("sort", "id,desc")   // <-- no space after comma!
	 * .param("sort", "name,asc")) // <-- no space after comma!
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Page<T> findAll(Pageable pageable) throws IllegalArgumentException {
		return repository.findAll(pageable);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public T save(T entity) {
		return repository.saveAndFlush(Objects.requireNonNull(entity, "A Entity cannot be null!"));
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Collection<T> saveAll(Collection<T> entities) {
		if (entities.size() > MAX_ENTITIES_AT_ONCE)
			throw new IllegalArgumentException("No more than " + MAX_ENTITIES_AT_ONCE + " at once!");
		
		return repository.saveAll(entities);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	public T update(T entity) {
		T entityToBeUpdated = repository.findById(Objects.requireNonNull(entity.getId(), "An entity to be updated must have an id!"))
			.orElseThrow(() -> new EntityNotFoundException("No entity found"));
		modelMapper.map(entity, entityToBeUpdated);
		return entityToBeUpdated;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	public Collection<T> updateAll(Collection<T> entities) {
		if (entities.size() > MAX_ENTITIES_AT_ONCE)
			throw new IllegalArgumentException("No more than " + MAX_ENTITIES_AT_ONCE + " at once!");
		
		for (T entity : entities) {
			T entityToBeUpdated = repository.findById(Objects.requireNonNull(entity.getId(), "An entity to be updated must have an id!"))
				.orElseThrow(() -> new EntityNotFoundException("No entity found"));
			modelMapper.map(entity, entityToBeUpdated);
		}
		return entities;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	public void deleteOne(T entity) {
		if (entity == null) return;
		repository.delete(entity);
	}
	
	/**
	 * @throws IllegalArgumentException If a given ID is null
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	public void deleteOne(Long id) throws IllegalArgumentException {
		repository.deleteById(id);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void deleteAll() {
		repository.deleteAll();
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	public void deleteAll(Iterable<T> entities) {
		if (entities == null) return;
		repository.deleteAll(entities);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	public void deleteAll(Collection<Long> ids) {
		ids.forEach(id -> repository.deleteById(id));
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Long count() {
		return repository.count();
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Boolean existById(Long id) {
		if (id == null) return false;
		return repository.existsById(id);
	}
	
}
