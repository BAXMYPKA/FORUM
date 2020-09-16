package ru.shop.forum.controllers;

import org.springframework.web.bind.annotation.RestController;
import ru.shop.forum.entities.User;
import ru.shop.forum.repositories.UserRepository;

@RestController
public class UserRestController extends AbstractForumRestController<User, UserRepository> {

	@Override
	protected void setEntityClass() {
		this.entityClass = User.class;
	}
}
