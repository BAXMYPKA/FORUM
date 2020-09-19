package ru.shop.forum.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.forum.entities.User;
import ru.shop.forum.entities.dto.UserDto;
import ru.shop.forum.services.UserService;

@RestController
@RequestMapping(path = "/users")
public class UserRestController extends AbstractForumRestController<User, UserDto, UserService> {
	
	@Override
	protected void setForumEntityClass(Class<User> forumEntityClass) {
		this.forumEntityClass = forumEntityClass;
	}
	
	@Override
	protected void setForumEntityDtoClass(Class<UserDto> forumEntityDtoClass) {
		this.forumEntityDtoClass = forumEntityDtoClass;
	}
	
	@Override
	protected void setForumEntityService(UserService forumEntityService) {
		this.forumEntityService = forumEntityService;
	}
}
