package ru.shop.forum.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import ru.shop.configs.ShopApplicationConfig;
import ru.shop.forum.services.PostService;
import ru.shop.security.configs.SecurityConfig;
import ru.shop.security.configs.TomcatHttpsConfig;
import ru.shop.services.UserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//@WithMockUser
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = {PostRestController.class})
//@Import(value = {SecurityConfig.class, TomcatHttpsConfig.class})
//@Import(value = {PostRestController.class})
public class RestControllersPathsTest {
	
	@LocalServerPort
	private int port;
	
	private String URL;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
//	@Autowired
//	private MockMvc mockMvc;
	
	@MockBean
	private PostService postService;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private ModelMapper modelMapper;
	
	
	@BeforeAll
	public void beforeAll() {
		this.URL = restTemplate.getRootUri();
	}
	
	@Test
	public void rer() throws Exception {
		System.out.println(URL);
//		mockMvc.perform(MockMvcRequestBuilders.get("/shop.ru/forum/v1.0/posts").secure(true))
//				.andDo(MockMvcResultHandlers.print());
	}
}
