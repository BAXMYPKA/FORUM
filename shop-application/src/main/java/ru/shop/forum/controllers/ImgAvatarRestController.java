package ru.shop.forum.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.forum.entities.ForumSection;
import ru.shop.forum.entities.ImgAvatar;
import ru.shop.forum.entities.dto.ForumSectionDto;
import ru.shop.forum.entities.dto.ImgAvatarDto;
import ru.shop.forum.services.ForumSectionService;
import ru.shop.forum.services.ImgAvatarService;

@RestController
@RequestMapping(path = "/img-avatars")
public class ImgAvatarRestController extends AbstractForumRestController<ImgAvatar, ImgAvatarDto, ImgAvatarService> {
	
	@Override
	protected void setEntityClass(Class<ImgAvatar> entityClass) {
		this.entityClass = entityClass;
	}
	
	@Override
	protected void setEntityDtoClass(Class<ImgAvatarDto> entityDtoClass) {
		this.entityDtoClass = entityDtoClass;
	}
	
	@Override
	protected void setEntityService(ImgAvatarService entityService) {
		this.entityService = entityService;
	}
}
