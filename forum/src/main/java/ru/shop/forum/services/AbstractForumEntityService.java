package ru.shop.forum.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.shop.forum.entities.AbstractForumEntity;
import ru.shop.forum.repositories.EntityRepository;
import ru.shop.forum.repositories.ForumEntityRepository;

import javax.transaction.Transactional;
import java.util.*;

@Getter
@Setter
@Service
public abstract class AbstractForumEntityService <T extends AbstractForumEntity, S extends ForumEntityRepository<T>> {
	
	@Value("${spring.data.web.pageable.default-page-size}")
	private Integer DEFAULT_ENTITIES_AT_ONCE;
	
	@Getter(AccessLevel.PROTECTED)
	protected S repository;
	
	/**
	 * @param repository If a special {@link EntityRepository} for a subclass is needed it must be included here.
	 *                   Otherwise if no specific repository is created just the typed {@link EntityRepository} will be enough here
	 */
	protected abstract void setRepository(S repository);
	
	@Transactional(value = Transactional.TxType.SUPPORTS)
	public Optional<T> findOne(Long id) {
		return repository.findById(id);
	}
	
	@Transactional(value = Transactional.TxType.SUPPORTS)
	public Optional<T> findOne(T forumEntityExample) {
		return repository.findOne(Example.of(forumEntityExample));
	}
	
	@Transactional(value = Transactional.TxType.REQUIRED)
	public Page<T> findAllByIds(Pageable pageable, Iterable<Long> ids) {
	
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
	public List<T> save(T... forumEntity) {
		return repository.saveAll(List.of(forumEntity));
	}
	
	@Transactional(value = Transactional.TxType.REQUIRED)
	public void deleteOne(T forumEntity) {
		if (forumEntity == null) return;
		repository.delete(forumEntity);
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
