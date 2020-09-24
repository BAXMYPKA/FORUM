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
import ru.shop.entities.AbstractEntity;
import ru.shop.repositories.EntityRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
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
	
	@Transactional(value = Transactional.TxType.SUPPORTS)
	public Optional<T> findOne(Long id) {
		return repository.findById(id);
	}
	
	@Transactional(value = Transactional.TxType.SUPPORTS)
	public Optional<T> findOne(T entityExample) {
		return repository.findOne(Example.of(entityExample));
	}
	
	@Transactional(value = Transactional.TxType.REQUIRED)
	public Page<T> findAllByIds(Pageable pageable, Collection<Long> ids) {
		if (ids.size() > MAX_ENTITIES_AT_ONCE) {
			ids = ids.stream().limit(50).collect(Collectors.toList());
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
	@Transactional(value = Transactional.TxType.REQUIRED)
	public Page<T> findAll(Pageable pageable) throws IllegalArgumentException {
		return repository.findAll(pageable);
	}
	
	
	@Transactional(value = Transactional.TxType.SUPPORTS)
	public T save(T entity) {
		return repository.saveAndFlush(Objects.requireNonNull(entity, "A Entity cannot be null!"));
	}
	
	@Transactional(value = Transactional.TxType.REQUIRED)
	public Collection<T> saveAll(Collection<T> entities) {
		if (entities.size() > MAX_ENTITIES_AT_ONCE)
			throw new IllegalArgumentException("No more than " + MAX_ENTITIES_AT_ONCE + " at once!");
		
		return repository.saveAll(entities);
	}
	
	@Transactional(value = Transactional.TxType.REQUIRED)
	public T update(T entity) {
		T entitiesToBeUpdated = repository.findById(Objects.requireNonNull(entity.getId(), "An entity to be updated must have an id!"))
			.orElseThrow(() -> new EntityNotFoundException("No entity found"));
		modelMapper.map(entity, entitiesToBeUpdated);
		return entitiesToBeUpdated;
	}
	
	@Transactional(value = Transactional.TxType.REQUIRED)
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
	
	@Transactional(value = Transactional.TxType.REQUIRED)
	public void deleteOne(T entity) {
		if (entity == null) return;
		repository.delete(entity);
	}
	
	/**
	 * @throws IllegalArgumentException If a given ID is null
	 */
	@Transactional(value = Transactional.TxType.REQUIRED)
	public void deleteOne(Long id) throws IllegalArgumentException {
		repository.deleteById(id);
	}
	
	@Transactional(value = Transactional.TxType.REQUIRED)
	public void deleteAll() {
		repository.deleteAll();
	}
	
	@Transactional(value = Transactional.TxType.REQUIRED)
	public void deleteAll(Iterable<T> entities) {
		if (entities == null) return;
		repository.deleteAll(entities);
	}
	
	@Transactional(value = Transactional.TxType.REQUIRED)
	public void deleteAll(Collection<Long> ids) {
		ids.forEach(id -> repository.deleteById(id));
	}
	
	@Transactional(value = Transactional.TxType.SUPPORTS)
	public Long count() {
		return repository.count();
	}
	
	@Transactional(value = Transactional.TxType.SUPPORTS)
	public Boolean existById(Long id) {
		if (id == null) return false;
		return repository.existsById(id);
	}
	
}
