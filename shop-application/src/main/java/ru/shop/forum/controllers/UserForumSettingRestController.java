package ru.shop.forum.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.forum.entities.ForumUserSettings;
import ru.shop.forum.entities.dto.ForumUserDto;
import ru.shop.forum.entities.dto.UserForumSettingsDto;
import ru.shop.forum.services.ForumUserSettingsService;

@RestController
@RequestMapping(path = {"/user-forum-settings", "/users/{id}/user-forum-settings"})
public class UserForumSettingRestController extends AbstractForumRestController<ForumUserSettings, UserForumSettingsDto, ForumUserSettingsService> {
	
	public UserForumSettingRestController(ForumUserSettingsService entityService, ModelMapper modelMapper) {
		super(entityService, modelMapper);
		this.entityDtoClass = UserForumSettingsDto.class;
	}
}
