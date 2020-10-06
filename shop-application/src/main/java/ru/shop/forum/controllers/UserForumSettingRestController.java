package ru.shop.forum.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.forum.entities.ForumUserSettings;
import ru.shop.forum.entities.dto.UserForumSettingsDto;
import ru.shop.forum.services.ForumUserSettingsService;

@RestController
@RequestMapping(path = {"/v1.0/users/{id}/user-forum-settings"})
public class UserForumSettingRestController extends AbstractForumRestController<ForumUserSettings, UserForumSettingsDto, ForumUserSettingsService> {
	
	@Override
	@GetMapping(path = "/v1.0/users/{id}/user-forum-settings")
	public UserForumSettingsDto getOne(@PathVariable Long id, Authentication authentication)
			throws AuthorizationServiceException {
		
		return super.getOne(id, authentication);
		
		//TODO: to set the ID into Authentication object

//		if (!id.equals(authentication.getDetails()))
//			throw new AuthorizationServiceException("The authenticated users ids mismatch!");
	}
	
	//TODO: to preauthorize
	@Override
//	@PreAuthorize("$}")
	@GetMapping(path = "/v1.0/users/user-forum-settings")
	public Page<UserForumSettingsDto> getAllPageable(Pageable pageable) {
		return super.getAllPageable(pageable);
	}
	
	public UserForumSettingRestController(ForumUserSettingsService entityService, ModelMapper modelMapper, ObjectMapper objectMapper) {
		super(entityService, modelMapper, objectMapper);
		this.entityDtoClass = UserForumSettingsDto.class;
	}
}
