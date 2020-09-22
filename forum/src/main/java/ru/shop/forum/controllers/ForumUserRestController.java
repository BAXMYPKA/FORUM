package ru.shop.forum.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.controllers.AbstractRestController;
import ru.shop.entities.User;
import ru.shop.forum.entities.ForumUser;
import ru.shop.forum.entities.dto.ForumUserDto;
import ru.shop.services.UserService;

@RestController
@RequestMapping(path = "/users")
public class ForumUserRestController extends AbstractRestController<User, ForumUserDto, UserService> {
	
	@Override
	protected void setEntityClass(Class<User> entityClass) {
		this.entityClass = entityClass;
	}
	
	@Override
	protected void setEntityDtoClass(Class<ForumUserDto> entityDtoClass) {
		this.entityDtoClass = entityDtoClass;
	}
	
	@Override
	protected void setEntityService(UserService entityService) {
		this.entityService = entityService;
	}
}
