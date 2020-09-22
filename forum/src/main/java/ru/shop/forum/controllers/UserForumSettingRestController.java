package ru.shop.forum.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.forum.entities.UserForumSettings;
import ru.shop.forum.entities.dto.UserForumSettingsDto;
import ru.shop.forum.services.ForumUserSettingsService;

@RestController
@RequestMapping(path = {"/user-forum-settings", "/users/{id}/user-forum-settings"})
public class UserForumSettingRestController extends AbstractForumRestController<UserForumSettings, UserForumSettingsDto, ForumUserSettingsService> {
	
	@Override
	protected void setEntityClass(Class<UserForumSettings> entityClass) {
		this.entityClass = entityClass;
	}
	
	@Override
	protected void setEntityDtoClass(Class<UserForumSettingsDto> entityDtoClass) {
		this.entityDtoClass = entityDtoClass;
	}
	
	@Override
	protected void setEntityService(ForumUserSettingsService entityService) {
		this.entityService = entityService;
	}
}
