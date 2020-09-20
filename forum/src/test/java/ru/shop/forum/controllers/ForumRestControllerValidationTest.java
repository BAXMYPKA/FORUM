package ru.shop.forum.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest
class ForumRestControllerValidationTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testOne() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk());
	}
}