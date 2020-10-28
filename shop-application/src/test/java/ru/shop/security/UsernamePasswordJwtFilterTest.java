package ru.shop.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.shop.repositories.RegistrationConfirmationUuidRepository;
import ru.shop.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {"system.current.time-zone=Europe/Moscow", "jwt.token.secret-word=secret",
	"jwt.token.realm=shop", "jwt.token.issuer=shop.ru", "jwt.token.expiration.days=7"})
class UsernamePasswordJwtFilterTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private JwtService jwtService;
	
	@MockBean
	private RegistrationConfirmationUuidRepository uuidRepository;
	
	@MockBean
	private UserRepository userRepository;
	
	@Test
	public void test() throws Exception {
		//given
		MultiValueMap<String, String> postParams = new LinkedMultiValueMap<>();
		postParams.add("username", "user");
		postParams.add("password", "pass");
		
		//when
		mockMvc.perform(MockMvcRequestBuilders.post("/shop.ru/forum/v1.0/login").params(postParams))
			.andDo(MockMvcResultHandlers.print());
	}
}