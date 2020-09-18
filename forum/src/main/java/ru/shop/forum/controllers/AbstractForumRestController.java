package ru.shop.forum.controllers;

import lombok.Getter;
import lombok.Setter;
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
import ru.shop.forum.entities.AbstractEntity;
import ru.shop.forum.entities.AbstractForumEntity;
import ru.shop.forum.entities.User;
import ru.shop.forum.entities.dto.AbstractDto;
import ru.shop.forum.entities.dto.AbstractForumDto;
import ru.shop.forum.entities.dto.UserDto;
import ru.shop.forum.entities.utils.ValidationCreateGroup;
import ru.shop.forum.repositories.ForumEntityRepository;
import ru.shop.forum.services.AbstractForumEntityService;

import javax.persistence.EntityNotFoundException;
import javax.validation.groups.Default;
import java.lang.reflect.Type;
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
 */
@Getter
@Setter
@RestController
//@RequestMapping(path = {"forum.shop.ru", "forum.shop.ru/v.1.0"})
public abstract class AbstractForumRestController <T extends AbstractForumEntity, S extends ForumEntityRepository<T>> {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AbstractForumEntityService<AbstractForumEntity, ForumEntityRepository<AbstractForumEntity>> forumEntityService;
	
	protected Class<? extends AbstractForumEntity> forumEntityClass;
	
	protected Class<? extends AbstractForumDto<T>> forumEntityDtoClass;
	
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public AbstractDto<T> getOne(@PathVariable Long id) {
		AbstractForumEntity entity = forumEntityService.findOne(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
		return (AbstractDto<T>) convertToForumDto(entity);
	}
	
	/**
	 * {@link Pageable} URL parameters:
	 * .param("page", "5")
	 * .param("size", "10")
	 * .param("sort", "id,desc")   // <-- no space after comma!
	 * .param("sort", "name,asc")) // <-- no space after comma!
	 */
	@GetMapping(params = {"page", "size", "sort"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<AbstractDto<T>> getAllPageable(
		@SortDefault.SortDefaults({
			@SortDefault(sort = "created", direction = Sort.Direction.DESC, caseSensitive = false),
			@SortDefault(sort = "id", direction = Sort.Direction.DESC, caseSensitive = false)})
			Pageable pageable) {
		
		Page<AbstractForumEntity> entities = forumEntityService.findAll(pageable);
		List<? super AbstractDto<T>> entitiesDto = entities.stream().map(this::convertToForumDto).collect(Collectors.toList());
		return new PageImpl<AbstractDto<T>>(entitiesDto, entities.getPageable(), entities.getTotalElements());
	}
	
	@GetMapping(path = "/all-by-ids", params = {"page", "size", "sort"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public PageImpl<AbstractDto<T>> getAllByIds(Pageable pageable, @RequestParam Map<String, Long> ids) {
		
		List<? extends AbstractForumDto<T>> entitiesDto = forumEntityService.findAllByIds(pageable, ids.values()).stream()
			.map(this::convertToForumDto).collect(Collectors.toList());
		return new PageImpl<AbstractDto<T>>(entitiesDto, pageable, entitiesDto.size());
	}
	
	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteOne(@PathVariable Long id) {
		
		forumEntityService.deleteOne(id);
		return ResponseEntity.ok().body("The " + forumEntityClass.getSimpleName() + " with the given ID = " + id + " has been deleted.");
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public AbstractDto<T> postNewOne(
		@Validated(value = {Default.class, ValidationCreateGroup.class}) @RequestBody AbstractForumDto<T> forumDto) {
		
		AbstractForumEntity forumEntity = convertToForumEntity(forumDto);
		User user = new User();
		forumEntityService.save(forumEntity)
		return null;
	}
	
	/**
	 * The concrete subclass of the {@link AbstractForumEntity}
	 * must be set as {@code this.entityClass = <T extends AbstractEntity>.getClass}
	 */
	protected abstract void setForumEntityClass();
	
	/**
	 * The concrete subclass of {@link AbstractDto} for the existing {@link #forumEntityClass}
	 * must be set as {@code this.entityDtoClass = <T extends AbstractDtoEntity>.getClass}
	 */
	protected abstract void setForumEntityDtoClass();
	
	protected AbstractForumEntity convertToForumEntity(AbstractForumDto<? extends AbstractForumEntity> abstractForumDto)
		throws ClassCastException {
		Objects.requireNonNull(abstractForumDto, "The AbstractDto subclass cannot be null!");
		return forumEntityClass.cast(modelMapper.map(abstractForumDto, forumEntityClass));
		
/*
		switch (Objects.requireNonNull(abstractDto.getClass().getSimpleName(), "The AbstractDto subclass cannot be null!")) {
			case "UserDto":
				User user = modelMapper.map(abstractDto, User.class);
			case "PostDto":
			case "UserForumSettingsDto":
			case "ImgAvatarDto":
			case "ForumSectionDto":
			case "PrivateMessageDto":
			
			default:
				throw new ClassCastException("No AbstractDto subclass found for name = " + abstractDto.getClass().getName());
		}
*/
	}
	
	
	protected AbstractForumDto convertToForumDto(AbstractForumEntity abstractForumEntity) {
		switch (Objects.requireNonNull(abstractForumEntity.getClass().getSimpleName(), "The AbstractEntity subclass cannot be null!")) {
			case "User":
				return modelMapper.map(abstractForumEntity, UserDto.class);
			case "Post":
			case "UserForumSettings":
			case "ImgAvatar":
			case "ForumSection":
			case "PrivateMessage":
			
			default:
				throw new ClassCastException("No AbstractEntity subclass found for name = " + abstractForumEntity.getClass().getName());
		}
	}
}
