package ru.shop.controllers;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.shop.entities.AbstractEntity;
import ru.shop.entities.dto.AbstractDto;
import ru.shop.entities.utils.ValidationCreateGroup;
import ru.shop.entities.utils.ValidationUpdateGroup;
import ru.shop.repositories.EntityRepository;
import ru.shop.services.AbstractEntityService;

import javax.persistence.EntityNotFoundException;
import javax.validation.groups.Default;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@NoArgsConstructor
@RestController
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
public abstract class AbstractRestController <
	T extends AbstractEntity,
	D extends AbstractDto<T>,
	S extends AbstractEntityService<T, ? extends EntityRepository<T>>
	> {
	
	protected ModelMapper modelMapper;
	
	@Getter(AccessLevel.PROTECTED)
	protected S entityService;
	
	protected Class<T> entityClass;
	
	/**
	 * MUST be set in every constructor in subclasses as {@code this.entityDtoClass = EntityDto<D>.class}
	 */
	@NonNull
	protected Class<D> entityDtoClass;
	
	@Autowired
	public AbstractRestController(S entityService, ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
		this.entityService = entityService;
		this.entityClass = entityService.getEntityClass();
	}
	
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public D getOne(@PathVariable Long id, Authentication authentication) {
		
		AbstractEntity entity = entityService.findOne(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
		return modelMapper.map(entity, entityDtoClass);
	}
	
	/**
	 * {@link Pageable} URL parameters:
	 * .param("page", "5")
	 * .param("size", "10")
	 * .param("sort", "id,desc")   // <-- no space after comma!
	 * .param("sort", "name,asc")) // <-- no space after comma!
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<D> getAllPageable(
		@PageableDefault
		@SortDefault.SortDefaults({
			@SortDefault(sort = "created", direction = Sort.Direction.DESC, caseSensitive = false),
			@SortDefault(sort = "id", direction = Sort.Direction.DESC, caseSensitive = false)})
			Pageable pageable) {
		
		Page<T> entitiesPage = entityService.findAll(pageable);
		List<D> entitiesDto = entitiesPage.stream()
			.map(entity -> modelMapper.map(entity, entityDtoClass))
			.collect(Collectors.toList());
		return new PageImpl<D>(entitiesDto, entitiesPage.getPageable(), entitiesPage.getTotalElements());
	}
	
	@GetMapping(path = "/all-by-ids", params = {"page", "size", "sort"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<D> getAllByIds(
		@SortDefault.SortDefaults({
			@SortDefault(sort = "created", direction = Sort.Direction.DESC, caseSensitive = false),
			@SortDefault(sort = "id", direction = Sort.Direction.DESC, caseSensitive = false)})
			Pageable pageable,
		@RequestParam Map<String, Long> ids) {
		
		Page<T> entitiesPage = entityService.findAllByIds(pageable, ids.values());
		List<D> entitiesDto = entitiesPage.stream()
			.map(entity -> modelMapper.map(entity, entityDtoClass))
			.collect(Collectors.toList());
		return new PageImpl<>(entitiesDto, entitiesPage.getPageable(), entitiesPage.getTotalElements());
	}
	
	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteOne(@PathVariable Long id) {
		
		entityService.deleteOne(id);
		return ResponseEntity.ok().body("The " + entityClass.getSimpleName() + " with the given ID = " + id + " has been deleted.");
	}
	
	@DeleteMapping(path = "/all-by-ids")
	public ResponseEntity<String> deleteAllByIds(@RequestParam Map<String, Long> ids) {
		entityService.deleteAll(ids.values());
		return ResponseEntity.ok("All entities with the given ids have been deleted!");
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public D postNewOne(
		@Validated(value = {Default.class, ValidationCreateGroup.class}) @RequestBody D entityDto) {
		
		T entity = modelMapper.map(entityDto, entityClass);
		T savedEntity = entityService.save(entity);
		return modelMapper.map(savedEntity, entityDtoClass);
	}
	
/*
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<D> postNewOnes(
			@Validated(value = {Default.class, ValidationCreateGroup.class}) @RequestBody Collection<D> forumDtos) {
		
		List<T> forumEntities = forumDtos.stream()
				.map(dto -> modelMapper.map(dto, entityClass)).collect(Collectors.toList());
		return entityService.saveAll(forumEntities).stream()
				.map(forumEntity -> modelMapper.map(forumEntity, entityDtoClass)).collect(Collectors.toList());
	}
*/
	
	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public D putOne(@Validated(value = {ValidationUpdateGroup.class, Default.class}) @RequestBody D entityDto) {
		
		T entity = modelMapper.map(entityDto, entityClass);
		entity = entityService.update(entity);
		return modelMapper.map(entity, entityDtoClass);
	}
	
/*
	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<D> putNewOnes(
			@Validated(value = {Default.class, ValidationUpdateGroup.class}) @RequestBody Collection<D> forumDtos) {
		
		List<T> forumEntities = forumDtos.stream()
				.map(dto -> modelMapper.map(dto, entityClass)).collect(Collectors.toList());
		return entityService.updateAll(forumEntities).stream()
				.map(forumEntity -> modelMapper.map(forumEntity, entityDtoClass)).collect(Collectors.toList());
	}
*/

}
