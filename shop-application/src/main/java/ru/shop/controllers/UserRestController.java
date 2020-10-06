package ru.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.shop.entities.User;
import ru.shop.entities.dto.UserDto;
import ru.shop.entities.utils.ValidationCreateGroup;
import ru.shop.entities.utils.ValidationUpdateGroup;
import ru.shop.services.UserService;

import javax.validation.Valid;
import javax.validation.groups.Default;

@RestController
@RequestMapping(path = "/v1.0/users", produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserRestController extends AbstractRestController<User, UserDto, UserService> {
	
	public UserRestController(UserService entityService, ModelMapper modelMapper, ObjectMapper objectMapper) {
		super(entityService, modelMapper, objectMapper);
		this.entityDtoClass = UserDto.class;
	}
	
	@Override
	public UserDto getOne(@PathVariable Long id, Authentication authentication) {
		return super.getOne(id, authentication);
	}
	
	@Override
	public Page<UserDto> getAllPageable(Pageable pageable, Authentication authentication) {
		return super.getAllPageable(pageable, authentication);
	}
	
	@Override
	public UserDto postNewOne(@Validated(value = {ValidationCreateGroup.class, Default.class}) @RequestBody UserDto entityDto,
									  Authentication authentication) {
		return super.postNewOne(entityDto, authentication);
	}
	
	@Override
	public UserDto putOne(@Validated(value = {ValidationUpdateGroup.class, Default.class}) @RequestBody UserDto entityDto,
								 Authentication authentication) {
		return super.putOne(entityDto, authentication);
	}
	
	@Override
	protected UserDto mapEntityToDto(User entity, Authentication authentication, PropertyMap<User, UserDto> propertyMap) {
		return super.mapEntityToDto(entity, authentication, propertyMap);
	}
}
