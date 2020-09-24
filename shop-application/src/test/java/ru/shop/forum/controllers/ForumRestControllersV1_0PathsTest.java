package ru.shop.forum.controllers;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.shop.ShopApplication;
import ru.shop.TestSecurityConfigs;
import ru.shop.configs.ShopApplicationConfig;
import ru.shop.controllers.AbstractRestController;
import ru.shop.controllers.ExceptionHandlerRestController;
import ru.shop.forum.services.*;
import ru.shop.security.configs.SecurityConfig;
import ru.shop.services.AbstractEntityService;
import ru.shop.services.UserService;

import javax.servlet.Filter;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.testSecurityContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {ForumIndexController.class, ForumUserRestController.class})
@AutoConfigureMockMvc
@WebAppConfiguration
@ContextConfiguration(classes = {TestSecurityConfigs.class})
//@Import(ForumUserRestController.class)
//@WithMockUser(username = "Admin", password = "Password", roles = {"ADMIN", "USER"})
@WithMockUser
class ForumRestControllersV1_0PathsTest {

//	@Autowired
//	private WebApplicationContext webApplicationContext;

//	@Autowired
//	private ForumUserRestController forumUserRestController;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ModelMapper modelMapper;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private ForumUserService forumUserService;
	
	@MockBean
	private ImgAvatarService imgAvatarService;
	
	@MockBean
	private ForumUserSettingsService forumUserSettingsService;
	
	private final String FORUM_APP_PATH = "/shop.ru/forum/v1.0";
	
	@BeforeEach
	public void setup() {
//		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//				.apply(SecurityMockMvcConfigurers.springSecurity())
//				.build();
//		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//				.addFilters(springSecurityFilterChain)
//				.defaultRequest(get("/").secure(true).with(testSecurityContext()))
//				.build();
	}
	
	@Test
	public void forum_index_Page_Should_Be_Returned() throws Exception {
		mockMvc.perform(get(FORUM_APP_PATH).secure(false))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().is3xxRedirection());
	}
	
	@Test
	public void forum_Users_Page_Should_Return_Found() throws Exception {
//		System.out.println(forumUserRestController.getClass());
		mockMvc.perform(get("/shop.ru/forum/v1.0/forum-users")
				.accept(MediaType.APPLICATION_JSON)
				.secure(true))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());
	}
	
	@Test
	public void users_Page_Should_Be() throws Exception {
		mockMvc.perform(
				get(FORUM_APP_PATH + "/forum-users/0")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
//				.andExpect(MockMvcResultMatchers.content().string("Nothing found for id = "));
	}
	
}