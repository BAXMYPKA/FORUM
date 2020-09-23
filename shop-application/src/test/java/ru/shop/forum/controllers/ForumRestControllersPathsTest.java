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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.shop.TestSecurityConfigs;
import ru.shop.controllers.AbstractRestController;
import ru.shop.controllers.ExceptionHandlerRestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {AbstractRestController.class, AbstractForumRestController.class, ForumUserRestController.class,
	ExceptionHandlerRestController.class})
@AutoConfigureMockMvc
@ContextConfiguration(classes = {TestSecurityConfigs.class})
class ForumRestControllersPathsTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ModelMapper modelMapper;
	
	private final String FORUM_APP_PATH = "/shop.ru/forum/v1.0";
	
	
	@WithMockUser(username = "Admin", password = "Password", roles = {"ADMIN"})
	@Test
	public void index_Page_Should_Be_Temporary_Not_Found() throws Exception {
		mockMvc.perform(get(FORUM_APP_PATH)).andExpect(status().isNotFound());
	}
	
	@WithMockUser(username = "Admin", password = "Password", roles = {"ADMIN"})
	@Test
	public void users_Page_Should_Be() throws Exception {
		mockMvc.perform(get(FORUM_APP_PATH + "/users/0"))
			.andExpect(status().isNotFound())
			.andExpect(MockMvcResultMatchers.content().string("Nothing found for id = "));
	}
	
}