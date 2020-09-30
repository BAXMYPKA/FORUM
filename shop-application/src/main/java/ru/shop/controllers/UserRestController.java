package ru.shop.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.entities.User;
import ru.shop.entities.dto.UserDto;
import ru.shop.services.UserService;

@RestController
@RequestMapping(path = "/v1.0/users", produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserRestController extends AbstractRestController<User, UserDto, UserService> {
	
	public UserRestController(UserService entityService, ModelMapper modelMapper) {
		super(entityService, modelMapper);
		this.entityDtoClass = UserDto.class;
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Override
	public UserDto postNewOne(UserDto entityDto) {
		System.out.println("POST CONTROLLER !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		return super.postNewOne(entityDto);
	}
}
