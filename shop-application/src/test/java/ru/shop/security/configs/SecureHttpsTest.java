package ru.shop.security.configs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.shop.forum.controllers.ForumIndexController;
import ru.shop.repositories.UserRepository;
import ru.shop.services.UserService;

@WebMvcTest(controllers = {ForumIndexController.class})
@WebAppConfiguration
class SecureHttpsTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private PasswordEncoder passwordEncoder;
	
	@Test
	public void http_Connection_to_Forum_Index_Page_Should_Be_Redirected_To_Https() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders
						.get("/shop.ru/forum/")
						.secure(false))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("https://localhost/shop.ru/forum/"));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"/shop.ru/forum/v1.0/users", "/shop.ru/forum/v1.0/posts", "/shop.ru/forum/v1.0/pms"})
	public void http_Connection_to_Any_Rest_Endpoint_v1_0_Should_Be_Redirected_To_Https(String uriTemplate) throws Exception {
		//given
		final String httpsUrl = "https://localhost";
		//when
		//then
		mockMvc.perform(
				MockMvcRequestBuilders
						.get(uriTemplate)
						.secure(false))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl(httpsUrl + uriTemplate));
	}
	
}