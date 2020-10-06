package ru.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.shop.entities.User;
import ru.shop.entities.dto.UserDto;
import ru.shop.services.UserService;

@RestController
@RequestMapping(path = "/v1.0/users", produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserRestController extends AbstractRestController<User, UserDto, UserService> {
	
	public UserRestController(UserService entityService, ModelMapper modelMapper, ObjectMapper objectMapper) {
		super(entityService, modelMapper, objectMapper);
		this.entityDtoClass = UserDto.class;
	}
	
	@GetMapping(path = "/{id}")
	@Override
	public UserDto getOne(@PathVariable Long id, Authentication authentication) {
		return super.getOne(id, authentication);
	}
	
	@GetMapping
	@Override
	public Page<UserDto> getAllPageable(Pageable pageable) {
		return super.getAllPageable(pageable);
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Override
	public ResponseEntity<UserDto> postNewOne(UserDto entityDto) {
		return super.postNewOne(entityDto);
	}
}
