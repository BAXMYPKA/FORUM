package ru.shop.forum.controllers;

import org.springframework.web.bind.annotation.RestController;
import ru.shop.forum.entities.AbstractForumEntity;
import ru.shop.forum.entities.User;
import ru.shop.forum.entities.dto.UserDto;
import ru.shop.forum.repositories.UserRepository;
import ru.shop.forum.services.AbstractForumEntityService;
import ru.shop.forum.services.UserService;

@RestController
public class UserRestController extends AbstractForumRestController<User, UserService> {
	
	
	@Override
	protected void setForumEntityClass(User forumEntityClass) {
		this.forumEntityClass = forumEntityClass;
	}
	
	@Override
	protected void setForumEntityDtoClass() {
	
	}
	
	@Override
	protected void setForumEntityService(AbstractForumEntityService forumEntityService) {
	
	}
}
