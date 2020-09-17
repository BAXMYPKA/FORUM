package ru.shop.forum.controllers;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.forum.entities.AbstractForumEntity;
import ru.shop.forum.entities.dto.AbstractDto;
import ru.shop.forum.entities.dto.UserDto;
import ru.shop.forum.repositories.EntityRepository;
import ru.shop.forum.services.AbstractEntityService;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

@Getter
@Setter
@RestController
//@RequestMapping(path = {"forum.shop.ru", "forum.shop.ru/v.1.0"})
public abstract class AbstractForumRestController<T extends AbstractForumEntity, S extends EntityRepository<T, Long>> {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AbstractEntityService<T, S> entityService;
	
	protected Class<? extends AbstractForumEntity> entityClass;
	
	protected Class<? extends AbstractDto<T>> entityDtoClass;
	
	@GetMapping(path = "/{id}")
	public AbstractDto<T> getOne(@PathVariable Long id) {
		T entity = entityService.findOne(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
		return convertToDto(entity);
	}
	
	/**
	 * The concrete subclass of the {@link AbstractForumEntity}
	 * must be set as {@code this.entityClass = <T extends AbstractEntity>.getClass}
	 */
	protected abstract void setEntityClass();
	
	/**
	 * The concrete subclass of {@link AbstractDto} for the existing {@link #entityClass}
	 * must be set as {@code this.entityDtoClass = <T extends AbstractDtoEntity>.getClass}
	 */
	protected abstract void setEntityDtoClass();
	
	protected AbstractForumEntity convertToEntity(AbstractDto abstractDto)
		  throws ClassCastException {
		Objects.requireNonNull(abstractDto, "The AbstractDto subclass cannot be null!");
		return entityClass.cast(modelMapper.map(abstractDto, entityClass));
		
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
	
	
	protected AbstractDto convertToDto(AbstractForumEntity abstractEntity) {
		switch (Objects.requireNonNull(abstractEntity.getClass().getSimpleName(), "The AbstractEntity subclass cannot be null!")) {
			case "User":
				return modelMapper.map(abstractEntity, UserDto.class);
			case "Post":
			case "UserForumSettings":
			case "ImgAvatar":
			case "ForumSection":
			case "PrivateMessage":
			
			default:
				throw new ClassCastException("No AbstractEntity subclass found for name = " + abstractEntity.getClass().getName());
		}
	}
	
}
