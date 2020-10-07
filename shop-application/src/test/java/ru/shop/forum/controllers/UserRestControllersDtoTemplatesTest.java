package ru.shop.forum.controllers;

import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.verification.VerificationMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import ru.shop.controllers.UserRestController;
import ru.shop.entities.User;
import ru.shop.entities.dto.UserDto;
import ru.shop.repositories.UserRepository;
import ru.shop.security.configs.TestSslUtil;
import ru.shop.services.UserService;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class UserRestControllersDtoTemplatesTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private UserRestController userRestController;
	
//	@Autowired
//	private UserRepository userRepository;

	@MockBean
	private UserService userService;
	
	@BeforeAll
	public static void beforeAll() throws KeyManagementException, NoSuchAlgorithmException {
		TestSslUtil.turnOffSslChecking();
	}
	
	@AfterAll
	public static void afterAll() throws NoSuchAlgorithmException, KeyManagementException {
		TestSslUtil.turnOnSslChecking();
	}
	
	@BeforeEach
	public void beforeEach() {
		userRestController.setEntityClass(User.class);
		Mockito.when(userService.getEntityClass()).thenReturn(User.class);
	}
	
/*
	@AfterEach
	public void afterEach() {
		userRepository.deleteAll();
	}
*/
	
	@Test
	public void get_User_By_Id_Should_Return_User() {
		//given
		User user = new User("user@email.com");
		user.setId(1L);
		user.setNickName("Nick");
		user.setPassword("123");
//		User savedUser = userRepository.save(user);
		
		Mockito.when(userService.findOne(1L)).thenReturn(Optional.of(user));
		
		//when
		UserDto userDto =
				restTemplate.getForObject(restTemplate.getRootUri() + "/v1.0/users/" + user.getId(), UserDto.class);
		
		//then
		assertEquals("user@email.com", userDto.getEmail());
	}
	
	@Disabled
	@Test
	public void get_All_Users_Should_Return_Users() {
		//given
		User user = new User("user@email.com");
		user.setNickName("Nick");
		user.setPassword("123");
		
		User user2 = new User("user2@email.com");
		user2.setNickName("Nick2");
		user2.setPassword("123");
		
		//when
		ResponseEntity<Page> userDtos =
				restTemplate.getForEntity(restTemplate.getRootUri() + "/v1.0/users/", Page.class);
		
		//then
//		assertEquals(2, userDtos.length);
	}
	
	@Test
	public void delete_User_By_Id_Should_Return_NoContent() {
		//given
		User user = new User("user@email.com");
		user.setId(1L);
		user.setNickName("Nick");
		user.setPassword("123");
		
		ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
		
		//when
		ResponseEntity<String> deleted
				= restTemplate.exchange(restTemplate.getRootUri() + "/v1.0/users", HttpMethod.DELETE, null, String.class);
		
		//then
		Mockito.verify(userService, VerificationModeFactory.atLeastOnce()).deleteOne(idCaptor.capture());
		assertEquals(1L, idCaptor.capture());
	}
	
}
