package ru.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.shop.entities.RegistrationConfirmationUuid;
import ru.shop.entities.dto.RegistrationConfirmationUuidDto;
import ru.shop.services.RegistrationConfirmationUuidService;
import ru.shop.utils.ShopEventPublisher;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@RestController
@RequestMapping(path = "/v1.0/uuids", produces = {MediaType.APPLICATION_JSON_VALUE})
public class RegistrationConfirmationUuidRestController	extends
	AbstractRestController
		<RegistrationConfirmationUuid, RegistrationConfirmationUuidDto, RegistrationConfirmationUuidService> {
	
	public RegistrationConfirmationUuidRestController(
			RegistrationConfirmationUuidService uuidService,
			ModelMapper modelMapper,
			ObjectMapper objectMapper,
			ShopEventPublisher shopEventPublisher) {
		super(uuidService, modelMapper, objectMapper, shopEventPublisher);
	}
	
	//TODO: to make available only for admin
	//TODO: to check the SPeL
	@Override
	@RolesAllowed({"MODERATOR", "ADMIN"})
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public RegistrationConfirmationUuidDto getOne(@RequestParam Long id, Authentication authentication) {
		return super.getOne(id, authentication);
	}
	
	@GetMapping(path = "/{uuid}/confirm")
	@ResponseStatus(code = HttpStatus.PERMANENT_REDIRECT)
	public ResponseEntity<String> confirmOne(
			@PathVariable String uuid, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		
		entityService.confirmUuid(uuid);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(URI.create("/login"));
		
		redirectAttributes.addFlashAttribute("success", "Your account has been successfully confirmed!" +
				" Now please enter your login and password.");
		return new ResponseEntity<>(httpHeaders, HttpStatus.PERMANENT_REDIRECT);
	}
	
}
