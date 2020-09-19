package ru.shop.forum.controllers;

import lombok.AccessLevel;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.shop.forum.entities.AbstractForumEntity;
import ru.shop.forum.entities.dto.AbstractDto;
import ru.shop.forum.entities.dto.AbstractForumDto;
import ru.shop.forum.entities.utils.ValidationCreateGroup;
import ru.shop.forum.entities.utils.ValidationUpdateGroup;
import ru.shop.forum.repositories.ForumEntityRepository;
import ru.shop.forum.services.AbstractForumEntityService;

import javax.persistence.EntityNotFoundException;
import javax.validation.groups.Default;
import java.util.*;
import java.util.stream.Collectors;

/**
 * {@link Pageable} URL parameters:
 * .param("page", "5")
 * .param("size", "10")
 * .param("sort", "id,desc")   // <-- no space after comma!
 * .param("sort", "name,asc")) // <-- no space after comma!
 *
 * @param <T>
 * @param <S>
 * @param <D>
 */
@Getter
//@Setter
@RestController
@RequestMapping(path = {"/v.1.0"})
public abstract class AbstractForumRestController<T extends AbstractForumEntity, D extends AbstractForumDto<T>, S extends AbstractForumEntityService<T, ? extends ForumEntityRepository<T>>> {
	
	@Autowired
	protected ModelMapper modelMapper;
	
	@Getter(AccessLevel.PROTECTED)
	protected S forumEntityService;
	
	protected Class<T> forumEntityClass;
	
	protected Class<D> forumEntityDtoClass;
	
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public D getOne(@PathVariable Long id) {
		
		AbstractForumEntity entity = forumEntityService.findOne(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
		return modelMapper.map(entity, forumEntityDtoClass);
	}
	
	/**
	 * {@link Pageable} URL parameters:
	 * .param("page", "5")
	 * .param("size", "10")
	 * .param("sort", "id,desc")   // <-- no space after comma!
	 * .param("sort", "name,asc")) // <-- no space after comma!
	 */
	@GetMapping(params = {"page", "size", "sort"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<D> getAllPageable(
			@SortDefault.SortDefaults({
					@SortDefault(sort = "created", direction = Sort.Direction.DESC, caseSensitive = false),
					@SortDefault(sort = "id", direction = Sort.Direction.DESC, caseSensitive = false)})
					Pageable pageable) {
		
		Page<T> entitiesPage = forumEntityService.findAll(pageable);
		List<D> entitiesDto = entitiesPage.stream()
				.map(entity -> modelMapper.map(entity, forumEntityDtoClass))
				.collect(Collectors.toList());
		return new PageImpl<D>(entitiesDto, entitiesPage.getPageable(), entitiesPage.getTotalElements());
	}
	
	@GetMapping(path = "/all-by-ids", params = {"page", "size", "sort"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<D> getAllByIds(
			@SortDefault.SortDefaults({
					@SortDefault(sort = "created", direction = Sort.Direction.DESC, caseSensitive = false),
					@SortDefault(sort = "id", direction = Sort.Direction.DESC, caseSensitive = false)})
					Pageable pageable, @RequestParam Map<String, Long> ids) {
		
		Page<T> entitiesPage = forumEntityService.findAllByIds(pageable, ids.values());
		List<D> entitiesDto = entitiesPage.stream()
				.map(entity -> modelMapper.map(entity, forumEntityDtoClass))
				.collect(Collectors.toList());
		return new PageImpl<>(entitiesDto, entitiesPage.getPageable(), entitiesPage.getTotalElements());
	}
	
	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteOne(@PathVariable Long id) {
		
		forumEntityService.deleteOne(id);
		return ResponseEntity.ok().body("The " + forumEntityClass.getSimpleName() + " with the given ID = " + id + " has been deleted.");
	}
	
	@DeleteMapping(path = "/all-by-ids")
	public ResponseEntity<String> deleteAllByIds(@RequestParam Map<String, Long> ids) {
		forumEntityService.deleteAll(ids.values());
		return ResponseEntity.ok("All entities with the given ids have been deleted!");
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public D postNewOne(
			@Validated(value = {Default.class, ValidationCreateGroup.class}) @RequestBody D forumDto) {
		
		T forumEntity = modelMapper.map(forumDto, forumEntityClass);
		T savedForumEntity = forumEntityService.save(forumEntity);
		return modelMapper.map(savedForumEntity, forumEntityDtoClass);
	}
	
	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public D putOne(@Validated(value = {ValidationUpdateGroup.class, Default.class}) @RequestBody D forumDto) {
		
		T forumEntity = modelMapper.map(forumDto, forumEntityClass);
		forumEntity = forumEntityService.update(forumEntity);
		return modelMapper.map(forumEntity, forumEntityDtoClass);
	}
	
	/**
	 * The concrete subclass of the {@link AbstractForumEntity}
	 * must be set as {@code this.entityClass = <T extends AbstractEntity>.getClass}
	 */
	protected abstract void setForumEntityClass(Class<T> forumEntityClass);
	
	/**
	 * The concrete subclass of {@link AbstractDto} for the existing {@link #forumEntityClass}
	 * must be set as {@code this.entityDtoClass = <T extends AbstractDtoEntity>.getClass}
	 */
	protected abstract void setForumEntityDtoClass(Class<D> forumEntityDtoClass);
	
	/**
	 * @param forumEntityService The concrete subclass of {@link AbstractForumEntityService}
	 *                           must be set as {@code this.forumEntityService = forumEntityService}
	 */
	protected abstract void setForumEntityService(S forumEntityService);
	
}
