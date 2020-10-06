package ru.shop.forum.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import ru.shop.controllers.UserRestController;

@WebMvcTest(controllers = {UserRestController.class})
public class UserRestControllerValidationTest {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void re() {
	}
}
