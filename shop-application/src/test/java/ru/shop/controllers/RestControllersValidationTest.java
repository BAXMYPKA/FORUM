package ru.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.shop.entities.dto.UserDto;
import ru.shop.entities.utils.Sex;
import ru.shop.forum.repositories.ForumSectionRepository;
import ru.shop.repositories.UserRepository;
import ru.shop.services.UserService;

import java.time.LocalDate;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@WebMvcTest(controllers = {UserRestController.class})
@Import({UserRestController.class})
//@ContextConfiguration(classes = {SecurityConfig.class, WebMvcConfig.class})
//@ContextConfiguration
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = {UserRestController.class})
//@WebAppConfiguration
//@AutoConfigureMockMvc
//@EnableAutoConfiguration(exclude = {SecurityConfig.class})
//@WithMockUser(username = "Admin", password = "Password", roles = {"ADMIN"})
//	@WithMockUser
//@ActiveProfiles(profiles = {"test"})
class RestControllersValidationTest {
	
	//	@LocalServerPort
//	private int port;
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserRepository userRepository;
	@MockBean
	private ForumSectionRepository forumSectionRepository;
	@MockBean
	private UserService userService;
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	//////////////////////////////
	
	@Autowired
	private WebApplicationContext webApplicationContext;
//	@Autowired
//	private Filter springSecurityFilterChain;
	
//	@Autowired
	@MockBean
	private ModelMapper modelMapper;

	private ru.shop.entities.User user;
	
	private UserDto userDto;
	
	@BeforeEach
	public void beforeEach() {
		user = new ru.shop.entities.User("user@email.com");
		user.setPassword("123");
		user.setFirstName("FirstName");
		user.setSex(Sex.MALE);
		user.setBirthdate(LocalDate.now().minusYears(20));
		user.setNickName("Nick");
		
		userDto = new UserDto();
//		modelMapper.map(user, userDto);

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//			.addFilters(springSecurityFilterChain).build();
	}
	
	@Test
	public void getOne() throws Exception {
		userDto.setEmail("notValidEmail");
		mockMvc.perform(MockMvcRequestBuilders.get("/v1.0/users/0")
			.accept(MediaType.APPLICATION_JSON)
		).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void tst() throws Exception {
		userDto.setEmail("notValidEmail");
		mockMvc.perform(MockMvcRequestBuilders.post("/shop.ru/forum/v1.0/users")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(userDto))
			.accept(MediaType.APPLICATION_JSON)
		).andDo(MockMvcResultHandlers.print());
	}
	
/*
	@Test
	public void not_Valid_User_Email_Should_Be_Rejected() {
		//given
		userDto.setEmail("notValidEmail");
		userDto.setFirstName("k");
		//when
		UserDto userDto = userRestController.postNewOne(this.userDto);
		//then
		System.out.println(userDto.getSex());
	}
*/
}