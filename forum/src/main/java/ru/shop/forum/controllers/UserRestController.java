package ru.shop.forum.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.forum.entities.User;
import ru.shop.forum.entities.dto.UserDto;
import ru.shop.forum.services.UserService;

@RestController
public class UserRestController extends AbstractForumRestController<User, UserDto, UserService> {
	
	
	protected UserRestController(ModelMapper modelMapper) {
		super(modelMapper);
	}
	
	@Override
	protected void setForumEntityClass(Class<User> forumEntityClass) {
		super.forumEntityClass = forumEntityClass;
	}
	
	@Override
	protected void setForumEntityDtoClass(Class<UserDto> forumEntityDtoClass) {
		super.forumEntityDtoClass = forumEntityDtoClass;
	}
	
	@Override
	protected void setForumEntityService(UserService forumEntityService) {
	
	}
}
