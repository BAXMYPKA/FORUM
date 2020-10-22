package ru.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.shop.repositories.UserRepository;
import ru.shop.services.RegistrationConfirmationUuidService;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = {RegistrationConfirmationUuidRestController.class, ExceptionHandlerRestController.class})
@Import({ModelMapper.class, ObjectMapper.class})
class RegistrationConfirmationUuidRestControllerTest {
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private RegistrationConfirmationUuidService uuidService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void correct_Uuid_Confirmation_Should_Return_Permanent_Redirection_To_Login_Page() throws Exception {
		//given
		String uuid = "CorrectUUID";
		//when
		//then
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/v1.0/uuids/CorrectUUID/confirm").secure(true))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isPermanentRedirect())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/login"))
				.andReturn();
		
		assertTrue(mvcResult.getFlashMap().containsKey("success"));
		assertTrue(mvcResult.getFlashMap().containsValue("Your account has been successfully confirmed! Now please enter your login and password."));
	}
	
	@Test
	public void correct_Uuid_Confirmation_Should_Return_Success_Message_To_User() throws Exception {
		//given
		String uuid = "CorrectUUID";
		//when
		//then
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/v1.0/uuids/CorrectUUID/confirm").secure(true))
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
		
		assertTrue(mvcResult.getFlashMap().containsKey("success"));
		assertTrue(mvcResult.getFlashMap().containsValue("Your account has been successfully confirmed! Now please enter your login and password."));
	}
	
	@Test
	public void incorrect_Uuid_Confirmation_Should_Return_Bad_Request() throws Exception {
		//given
		String uuid = "IncorrectUUID";
		
		//when
		Mockito.doThrow(new IllegalArgumentException("No Registration Confirmation found for this uuid! " + uuid))
			.when(uuidService).confirmUuid(uuid);
		
		//then
		mockMvc.perform(MockMvcRequestBuilders.get("/v1.0/uuids/IncorrectUUID/confirm").secure(true))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content()
				.json("{\"errorMessage\":\"No Registration Confirmation found for this uuid! " + uuid + "\"}", false));
	}
	
}