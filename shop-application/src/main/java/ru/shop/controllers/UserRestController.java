package ru.shop.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
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
	
	@GetMapping(path = "/{id}")
	@Override
	public UserDto getOne(@PathVariable Long id, Authentication authentication) {
		System.out.println("GET ONE POST CONTROLLER !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		return super.getOne(id, authentication);
	}
	
	@GetMapping
	@Override
	public Page<UserDto> getAllPageable(Pageable pageable) {
		System.out.println("GET ALL POST CONTROLLER !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		return super.getAllPageable(pageable);
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Override
	public UserDto postNewOne(UserDto entityDto) {
		System.out.println("POST CONTROLLER !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		return super.postNewOne(entityDto);
	}
}
