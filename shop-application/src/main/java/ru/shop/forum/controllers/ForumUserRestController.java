package ru.shop.forum.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.controllers.AbstractRestController;
import ru.shop.forum.entities.ForumUser;
import ru.shop.forum.entities.dto.ForumUserDto;
import ru.shop.forum.services.ForumUserService;

//@NoArgsConstructor
@RestController
@RequestMapping(path = "/v1.0/forum-users", produces = MediaType.APPLICATION_JSON_VALUE)
public class ForumUserRestController extends AbstractRestController<ForumUser, ForumUserDto, ForumUserService> {
	
	
	public ForumUserRestController(ForumUserService entityService, ModelMapper modelMapper) {
		super(entityService, modelMapper);
		this.entityDtoClass = ForumUserDto.class;
	}
}
