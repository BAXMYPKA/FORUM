package ru.shop.forum.controllers;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.forum.entities.AbstractEntity;
import ru.shop.forum.entities.dto.AbstractDto;
import ru.shop.forum.entities.dto.UserDto;
import ru.shop.forum.repositories.EntityRepository;
import ru.shop.forum.services.AbstractEntityService;

import java.util.Objects;

@RestController
@Getter
@Setter
public abstract class AbstractForumRestController<T extends AbstractEntity, S extends EntityRepository<T, Long>> {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AbstractEntityService<T, S> entityService;
	
	protected Class<? extends AbstractEntity> entityClass;
	
	/**
	 * The concrete subclass of the {@link AbstractEntity} must be set as {@code this.entityClass = <T extends AbstractEntity>.getClass}
	 */
	protected abstract void setEntityClass();
	
	protected AbstractEntity convertToEntity(AbstractDto abstractDto)
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
	
	
	protected AbstractDto convertToDto(AbstractEntity abstractEntity) {
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
