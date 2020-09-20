package ru.shop.forum.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.forum.entities.UserForumSettings;
import ru.shop.forum.entities.dto.UserForumSettingsDto;
import ru.shop.forum.services.UserForumSettingsService;

@RestController
@RequestMapping(path = {"/user-forum-settings", "/users/{id}/user-forum-settings"})
public class UserForumSettingRestController extends AbstractForumRestController<UserForumSettings, UserForumSettingsDto, UserForumSettingsService> {
	@Override
	protected void setForumEntityClass(Class<UserForumSettings> forumEntityClass) {
		this.forumEntityClass = forumEntityClass;
	}
	
	@Override
	protected void setForumEntityDtoClass(Class<UserForumSettingsDto> forumEntityDtoClass) {
		this.forumEntityDtoClass = forumEntityDtoClass;
	}
	
	@Override
	protected void setForumEntityService(UserForumSettingsService forumEntityService) {
		this.forumEntityService = forumEntityService;
	}
}
