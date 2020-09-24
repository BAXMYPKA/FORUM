package ru.shop.forum.controllers;

import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.controllers.AbstractRestController;
import ru.shop.forum.entities.ForumUser;
import ru.shop.forum.entities.dto.ForumUserDto;
import ru.shop.forum.services.ForumUserService;

//@NoArgsConstructor
@RestController
@RequestMapping(path = "/v1.0/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class ForumUserRestController extends AbstractRestController<ForumUser, ForumUserDto, ForumUserService> {
	
	
	public ForumUserRestController(ForumUserService entityService, ModelMapper modelMapper) {
		super(entityService, modelMapper);
		this.entityDtoClass = ForumUserDto.class;
	}
}
