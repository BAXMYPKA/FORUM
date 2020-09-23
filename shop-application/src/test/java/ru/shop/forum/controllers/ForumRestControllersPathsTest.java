package ru.shop.forum.controllers;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.shop.TestSecurityConfigs;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {TestSecurityConfigs.class})
class ForumRestControllerValidationTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ModelMapper modelMapper;
	
	private final String APP_PATH = "/forum.shop.ru";
	
	
	@WithMockUser(username = "Admin", password = "Password", roles = {"ADMIN"})
	@Test
	public void testOne() throws Exception {
		
		mockMvc.perform(get(APP_PATH)).andExpect(status().isOk());
	}
}