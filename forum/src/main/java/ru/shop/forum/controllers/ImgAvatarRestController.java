package ru.shop.forum.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.forum.entities.ImgAvatar;
import ru.shop.forum.entities.dto.ImgAvatarDto;
import ru.shop.forum.services.ImgAvatarService;

@RestController
@RequestMapping(path = "/img-avatar")
public class ImgAvatarRestController extends AbstractForumRestController<ImgAvatar, ImgAvatarDto, ImgAvatarService> {
	@Override
	protected void setForumEntityClass(Class<ImgAvatar> forumEntityClass) {
		this.forumEntityClass = forumEntityClass;
	}
	
	@Override
	protected void setForumEntityDtoClass(Class<ImgAvatarDto> forumEntityDtoClass) {
		this.forumEntityDtoClass = forumEntityDtoClass;
	}
	
	@Override
	protected void setForumEntityService(ImgAvatarService forumEntityService) {
		this.forumEntityService = forumEntityService;
	}
}
