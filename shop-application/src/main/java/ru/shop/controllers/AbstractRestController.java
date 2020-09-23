package ru.shop.controllers;

import lombok.AccessLevel;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping(path = "/shop.ru", produces = {MediaType.APPLICATION_JSON_VALUE})
public abstract class AbstractRestController <
	T extends AbstractEntity,
	D extends AbstractDto<T>,
	S extends AbstractEntityService<T, ? extends EntityRepository<T>>
	> {
	
	@Autowired
	protected ModelMapper modelMapper;
	
	@Getter(AccessLevel.PROTECTED)
	protected S entityService;
	
	protected Class<T> entityClass;
	
	protected Class<D> entityDtoClass;
	
	/**
	 * The concrete subclass of the {@link AbstractEntity}
	 * must be set as {@code this.entityClass = <T extends AbstractEntity>.getClass}
	 */
	protected abstract void setEntityClass(Class<T> entityClass);
	
	/**
	 * The concrete subclass of {@link AbstractDto} for the existing {@link #entityClass}
	 * must be set as {@code this.entityDtoClass = <T extends AbstractDtoEntity>.getClass}
	 */
	protected abstract void setEntityDtoClass(Class<D> entityDtoClass);
	
	/**
	 * @param entityService The concrete subclass of {@link AbstractEntityService}
	 *                      must be set as {@code this.forumEntityService = forumEntityService}
	 */
	protected abstract void setEntityService(S entityService);
	
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public D getOne(@PathVariable Long id) {
		
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
	@GetMapping(params = {"page", "size", "sort"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<D> getAllPageable(
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
			Pageable pageable, @RequestParam Map<String, Long> ids) {
		
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
		@Validated(value = {Default.class, ValidationCreateGroup.class}) @RequestBody D forumDto) {
		
		T forumEntity = modelMapper.map(forumDto, entityClass);
		T savedForumEntity = entityService.save(forumEntity);
		return modelMapper.map(savedForumEntity, entityDtoClass);
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
	public D putOne(@Validated(value = {ValidationUpdateGroup.class, Default.class}) @RequestBody D forumDto) {
		
		T forumEntity = modelMapper.map(forumDto, entityClass);
		forumEntity = entityService.update(forumEntity);
		return modelMapper.map(forumEntity, entityDtoClass);
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
