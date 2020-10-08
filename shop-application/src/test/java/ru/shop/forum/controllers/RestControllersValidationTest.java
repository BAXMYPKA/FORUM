package ru.shop.forum.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import ru.shop.controllers.UserRestController;
import ru.shop.entities.dto.UserDto;
import ru.shop.repositories.UserRepository;
import ru.shop.services.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {UserRestController.class})
@Import({ModelMapper.class})
public class RestControllersValidationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserRestController userRestController;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@BeforeEach
	public void beforeEach() {
		userRestController.setEntityClass(ru.shop.entities.User.class);
	}
	
	@Test
	public void post_Incorrect_UserDto_Should_Return_NotAcceptable() throws Exception {
		//given no email, no nickname etc
		UserDto incorrectUserDto = new UserDto();
		incorrectUserDto.setNickName("Nick");
		//when
		mockMvc.perform(
				post("/v1.0/users")
						.with(SecurityMockMvcRequestPostProcessors.csrf())
						.secure(true)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(incorrectUserDto)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isNotAcceptable());
	}
	
	@Test
	public void put_Incorrect_UserDto_Should_Return_NotAcceptable() throws Exception {
		//given no email, no nickname etc
		UserDto incorrectUserDto = new UserDto();
		incorrectUserDto.setId(1L);
		incorrectUserDto.setNickName("Nick");
		//when
		mockMvc.perform(
				put("/v1.0/users")
						.with(SecurityMockMvcRequestPostProcessors.csrf())
						.secure(true)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(incorrectUserDto)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isNotAcceptable());
	}
	
}
