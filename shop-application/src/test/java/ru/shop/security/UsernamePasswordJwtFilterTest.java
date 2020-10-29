package ru.shop.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.shop.repositories.RegistrationConfirmationUuidRepository;
import ru.shop.repositories.UserRepository;

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
	
	@Test
	public void forum() throws Exception {
		//given
		MultiValueMap<String, String> postParams = new LinkedMultiValueMap<>();
		postParams.add("username", "user");
		postParams.add("password", "pass");
		
		//when
		MvcResult mvcResult = mockMvc.perform(
			MockMvcRequestBuilders.post("/forum/login").params(postParams).secure(true).with(csrf()))
			.andDo(MockMvcResultHandlers.print())
			.andReturn();
		
		System.out.println(mvcResult.getFlashMap().getTargetRequestParams());
		System.out.println(mvcResult.getFlashMap());
		System.out.println(mvcResult.getFlashMap().size());
		
	}
}