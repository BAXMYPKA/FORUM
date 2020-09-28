package ru.shop.forum.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.forum.entities.ImgAvatar;
import ru.shop.forum.entities.dto.ImgAvatarDto;
import ru.shop.forum.services.ImgAvatarService;

@RestController
@RequestMapping(path = "/v1.0/img-avatars")
public class ImgAvatarRestController extends AbstractForumRestController<ImgAvatar, ImgAvatarDto, ImgAvatarService> {
	
	public ImgAvatarRestController(ImgAvatarService entityService, ModelMapper modelMapper) {
		super(entityService, modelMapper);
		this.entityDtoClass = ImgAvatarDto.class;
	}
}
