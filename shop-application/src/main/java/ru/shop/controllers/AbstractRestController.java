package ru.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
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
import org.springframework.lang.Nullable;
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
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
public abstract class AbstractRestController<
		T extends AbstractEntity,
		D extends AbstractDto<T>,
		S extends AbstractEntityService<T, ? extends EntityRepository<T>>
		> {
	
	protected ModelMapper modelMapper;
	
	protected ObjectMapper objectMapper;
	
	@Getter(AccessLevel.PROTECTED)
	protected S entityService;
	
	@Setter
	protected Class<T> entityClass;
	
	/**
	 * MUST be set in every constructor in subclasses as {@code this.entityDtoClass = EntityDto<D>.class}
	 */
	@NonNull
	protected Class<D> entityDtoClass;
	
	@Autowired
	public AbstractRestController(S entityService, ModelMapper modelMapper, ObjectMapper objectMapper) {
		this.modelMapper = modelMapper;
		this.entityService = entityService;
		this.objectMapper = objectMapper;
		this.entityClass = entityService.getEntityClass();
	}
	
	//	@JsonView(ShopViews.ExternalView.class)
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public D getOne(@PathVariable Long id, Authentication authentication) {
		
		T entity = entityService.findOne(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
		return mapEntityToDto(entity, authentication, null);
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
					Pageable pageable,
			Authentication authentication) {
		
		Page<T> entitiesPage = entityService.findAll(pageable);
		List<D> entitiesDto = entitiesPage.stream()
				.map(entity -> mapEntityToDto(entity, authentication, null))
				.collect(Collectors.toList());
		return new PageImpl<D>(entitiesDto, entitiesPage.getPageable(), entitiesPage.getTotalElements());
	}
	
	/**
	 * @param id "/all-by-ids?id=1,2" etc.
	 */
	@GetMapping(path = "/all-by-ids", produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<D> getAllByIds(
			@PageableDefault
			@SortDefault.SortDefaults({
					@SortDefault(sort = "created", direction = Sort.Direction.DESC, caseSensitive = false),
					@SortDefault(sort = "id", direction = Sort.Direction.DESC, caseSensitive = false)})
					Pageable pageable,
			@RequestParam Set<Long> id,
			Authentication authentication) {
		
		Page<T> entitiesPage = entityService.findAllByIds(pageable, id);
		List<D> entitiesDto = entitiesPage.stream()
				.map(entity -> mapEntityToDto(entity, authentication, null))
				.collect(Collectors.toList());
		return new PageImpl<>(entitiesDto, entitiesPage.getPageable(), entitiesPage.getTotalElements());
	}
	
	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteOne(@PathVariable Long id, Authentication authentication) {
		entityService.deleteOne(id);
		//The body wont be returned with this status
		return new ResponseEntity<>(
				"The " + entityClass.getSimpleName() + " with the given ID = " + id + " has been deleted.", HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping(path = "/all-by-ids", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteAllByIds(@RequestParam Set<Long> id) {
		entityService.deleteAll(id);
		return new ResponseEntity<>("All entities with the given ids = " + id + " have been deleted!", HttpStatus.NO_CONTENT);
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public D postNewOne(
			@Validated(value = {ValidationCreateGroup.class, Default.class}) @RequestBody D entityDto, Authentication authentication) {
		
		T entity = modelMapper.map(entityDto, entityClass);
		T savedEntity = entityService.save(entity);
		return mapEntityToDto(savedEntity, authentication, null);
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
	@ResponseStatus(code = HttpStatus.OK)
	public D putOne(@Validated(value = {ValidationUpdateGroup.class, Default.class}) @RequestBody D entityDto,
						 Authentication authentication) {
		
		T entity = modelMapper.map(entityDto, entityClass);
		entity = entityService.update(entity);
		return mapEntityToDto(entity, authentication, null);
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
	
	/**
	 * Can be overridden to write custom mappings for every type of DTO
	 *
	 * @param propertyMap A possible set of rules how to map properties
	 * @return {@link AbstractDto<T>} with mapped properties according to either a given "propertyMap" (if present) or DTO with the default mapping.
	 */
	protected D mapEntityToDto(T entity, Authentication authentication, @Nullable PropertyMap<T, D> propertyMap) {
		return modelMapper.map(entity, entityDtoClass);
	}
	
}
