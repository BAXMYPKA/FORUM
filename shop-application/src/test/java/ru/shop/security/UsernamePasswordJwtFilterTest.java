package ru.shop.security;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.shop.entities.User;
import ru.shop.repositories.RegistrationConfirmationUuidRepository;
import ru.shop.repositories.UserRepository;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
//@TestPropertySource(properties = {"system.current.time-zone=Europe/Moscow", "jwt.token.secret-word=secret",
//	"jwt.token.realm=shop", "jwt.token.issuer=shop.ru", "jwt.token.expiration.days=7"})
class UsernamePasswordJwtFilterTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private JwtService jwtService;
	
	@MockBean
	private RegistrationConfirmationUuidRepository uuidRepository;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private PasswordEncoder passwordEncoder;
	
	@Test
	public void wrong_Forum_Username_While_Logging_In_Should_Return_401_Unauthorized_Status() throws Exception {
		//given non-existing user
		MultiValueMap<String, String> postParams = new LinkedMultiValueMap<>();
		postParams.add("username", "user");
		postParams.add("password", "pass");
		
		//when
		//then
		mockMvc.perform(
				MockMvcRequestBuilders.post("/shop/forum/authentication").params(postParams).secure(true).with(csrf()))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}
	
	@Test
	public void correct_Forum_Username_While_Logging_In_Should_Be_Permanently_Redirected_302_Status() throws Exception {
		//given existing user
		
		String correctUsername = "user@email.ru";
		String correctPassword = "pass";
		
		User user = new User();
		user.setEmail(correctUsername);
		user.setRole(Roles.USER);
		user.setPassword(correctPassword);
		user.setEnabled(true);
		user.setLocked(false);
		
		Mockito.when(userRepository.findByEmail(correctUsername, "new-user-with-registrationUuid"))
				.thenReturn(Optional.of(user));
		Mockito.when(passwordEncoder.matches(correctPassword, correctPassword)).thenReturn(true);
		
		MultiValueMap<String, String> postParams = new LinkedMultiValueMap<>();
		postParams.add("username", correctUsername);
		postParams.add("password", correctPassword);
		
		//when
		//then
		mockMvc.perform(
				MockMvcRequestBuilders.post("/shop/forum/authentication").params(postParams).secure(true).with(csrf()))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
		.andExpect(MockMvcResultMatchers.redirectedUrl("/shop.ru/forum/v1.0/"));
	}
	
}