package ru.shop.forum.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import ru.shop.forum.services.PostService;
import ru.shop.services.UserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class RestControllersDtoTemplatesTest {
	
	@LocalServerPort
	private int port;
	
	private String URL;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@MockBean
	private PostService postService;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private ModelMapper modelMapper;
	
	
	@BeforeEach
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
