package ru.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import ru.shop.entities.User;
import ru.shop.repositories.UserRepository;
import ru.shop.security.JwtService;
import ru.shop.services.UserService;
import ru.shop.utils.ShopEventPublisher;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebMvcTest(controllers = {UserRestController.class, ExceptionHandlerRestController.class})
@Import({ModelMapper.class, ShopEventPublisher.class, JwtService.class})
class ExceptionHandlerRestControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private WebApplicationContext wac;
	
	@BeforeEach
	public void setUp() {
		mockMvc = webAppContextSetup(wac).addFilter(((request, response, chain) -> {
			response.setCharacterEncoding("UTF-8");
			chain.doFilter(request, response);
		})).build();
	}
	
	@Test
	public void entityNotFound_Should_Return_Status_Not_Found_With_Json_Message() throws Exception {
		//given
		Mockito.when(userService.findOne(0L)).thenReturn(Optional.empty());
		
		//when
		mockMvc.perform(MockMvcRequestBuilders.get("/v1.0/users/0")
				.secure(true)
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().json("{\"errorMessage\":\"Nothing found for id = 0\"}", false));
		//then
	}
	
	@Test
	public void not_Valid_Entity_Should_Return_Status_Not_Acceptable_With_Fields_Errors_Json_Message() throws Exception {
		//given
		User notValidUser = new User();
		notValidUser.setEmail("notValidEmail");
		
		//when
		String contentAsString = mockMvc.perform(
				MockMvcRequestBuilders.post("/v1.0/users", notValidUser)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(notValidUser))
						.characterEncoding("UTF-8")
						.secure(true)
						.with(SecurityMockMvcRequestPostProcessors.csrf()))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isNotAcceptable())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().json(
						"{\"errorMessage\":\"A not acceptable entity has been received with not valid fields.\"}", false))
				.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		//then
		assertAll(
				() -> assertTrue(contentAsString.contains("\"fieldValidationErrors\":[{")),
				() -> assertTrue(contentAsString.contains("\"fieldName\":\"email\",\"errorMessage\":\"Неверный формат электронной почты!\",\"rejectedValue\":\"notValidEmail\"")),
				() -> assertTrue(contentAsString.contains("\"fieldName\":\"nickName\",\"errorMessage\":\"Поле не должно быть пустым!\",\"rejectedValue\":\"null\""))
		);
	}
	
}