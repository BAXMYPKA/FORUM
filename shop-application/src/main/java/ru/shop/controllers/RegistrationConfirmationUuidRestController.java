package ru.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.shop.entities.RegistrationConfirmationUuid;
import ru.shop.entities.dto.RegistrationConfirmationUuidDto;
import ru.shop.services.RegistrationConfirmationUuidService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/v1.0/uuids")
public class RegistrationConfirmationUuidRestController extends AbstractRestController<RegistrationConfirmationUuid, RegistrationConfirmationUuidDto, RegistrationConfirmationUuidService>{
	
	@Value("#{servletContext.contextPath}")
	private String servletContextPath;
	
	public RegistrationConfirmationUuidRestController(
		RegistrationConfirmationUuidService entityService, ModelMapper modelMapper, ObjectMapper objectMapper) {
		super(entityService, modelMapper, objectMapper);
	}
	
	//TODO: to check the SPeL
	@Override
	@PreAuthorize(value = "#principal.authorities.contains('ADMIN')")
	@GetMapping
	public RegistrationConfirmationUuidDto getOne(@RequestParam Long id, Authentication authentication) {
		return super.getOne(id, authentication);
	}
	
	@GetMapping(path = "/confirm")
	public ResponseEntity confirmOne(
		@RequestParam String uuid, HttpServletResponse response, RedirectAttributes redirectAttributes)
		throws IOException {
		
		entityService.confirmUuid(uuid);
		Map<String, String> headers = new HashMap<>();
		headers.put("Location", servletContextPath+"/login");
//		response.setHeader("Location", servletContextPath+"/login");
//		response.setStatus(302);
// 		response.sendRedirect(servletContextPath+"/login");
		redirectAttributes.addFlashAttribute("success", "Your account has been successfully confirmed!" +
			" Now please enter your login and password.");
		return new ResponseEntity(headers, HttpStatus.PERMANENT_REDIRECT);
	}
	
}
