package ru.shop.forum.controllers;

import ru.shop.forum.entities.User;

public class UserRestController extends AbstractForumRestController {

	@Override
	protected void setEntityClass() {
		this.entityClass = User.class;
	}
}
