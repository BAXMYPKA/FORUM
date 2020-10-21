package ru.shop.forum.controllers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.shop.repositories.UserRepository;

@WebMvcTest(controllers = {ForumIndexController.class})
public class ForumIndexControllerPathsTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserRepository userRepository;
	
	@ParameterizedTest
	@ValueSource(strings = {"/", "/index"})
	public void forum_Index_Page_Should_Return_Ok(String path) throws Exception {
		//given
		mockMvc.perform(MockMvcRequestBuilders.get(path).secure(true))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	
}
