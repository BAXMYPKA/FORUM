package ru.shop.forum.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.forum.entities.ImgAvatar;
import ru.shop.forum.entities.dto.ImgAvatarDto;
import ru.shop.forum.services.ImgAvatarService;

@RestController
@RequestMapping(path = "/v1.0/img-avatars")
public class ImgAvatarRestController extends AbstractForumRestController<ImgAvatar, ImgAvatarDto, ImgAvatarService> {
	
	public ImgAvatarRestController(ImgAvatarService entityService, ModelMapper modelMapper, ObjectMapper objectMapper) {
		super(entityService, modelMapper, objectMapper);
		this.entityDtoClass = ImgAvatarDto.class;
	}
}
