package ru.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.entities.dto.UserDto;
import ru.shop.entities.utils.ValidationCreateGroup;
import ru.shop.entities.utils.ValidationUpdateGroup;
import ru.shop.services.UserService;

import javax.validation.groups.Default;

@RestController
@RequestMapping(path = "/v1.0/users", produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserRestController extends AbstractRestController<ru.shop.entities.User, UserDto, UserService> {
	
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
	protected UserDto mapEntityToDto(ru.shop.entities.User entity, Authentication authentication, PropertyMap<ru.shop.entities.User, UserDto> propertyMap) {
		return super.mapEntityToDto(entity, authentication, propertyMap);
	}
}
