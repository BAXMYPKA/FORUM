package ru.shop.forum.controllers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import ru.shop.controllers.ExceptionHandlerRestController;
import ru.shop.controllers.UserRestController;
import ru.shop.entities.User;
import ru.shop.entities.dto.UserDto;
import ru.shop.security.configs.TestSslUtil;
import ru.shop.services.UserService;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
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
	
	@Autowired
	private ModelMapper modelMapper;
	
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
		userRestController.setEntityClass(ru.shop.entities.User.class);
		Mockito.when(userService.getEntityClass()).thenReturn(User.class);
	}
	
	@Test
	public void get_User_By_Id_Should_Return_Status_Ok_With_User() {
		//given
		User user = new User("user@email.com");
		user.setId(1L);
		user.setNickName("Nick");
		user.setPassword("123");
		
		Mockito.when(userService.findOne(1L)).thenReturn(Optional.of(user));
		
		//when
		ResponseEntity<UserDto> userDtoResponse =
				restTemplate.getForEntity(restTemplate.getRootUri() + "/v1.0/users/1", UserDto.class);
		
		//then
		assertEquals(HttpStatus.OK, userDtoResponse.getStatusCode());
		assertEquals("user@email.com", userDtoResponse.getBody().getEmail());
	}
	
	@ParameterizedTest
	@ValueSource(longs = {1, 2, 2222})
	public void get_Not_Existing_User_By_Id_Should_Return_Status_Not_Found_With_Message(Long wrongId) {
		//given
		Mockito.when(userService.findOne(wrongId)).thenReturn(Optional.empty());
		
		//when
		ResponseEntity<ExceptionHandlerRestController.ResponseObject> notFoundResponse =
				restTemplate.getForEntity(restTemplate.getRootUri() + "/v1.0/users/" + wrongId, ExceptionHandlerRestController.ResponseObject.class);
		
		//then
		assertEquals(HttpStatus.NOT_FOUND, notFoundResponse.getStatusCode());
		assertEquals("Nothing found for id = " + wrongId, notFoundResponse.getBody().getErrorMessage());
	}
	
	@Test
	public void delete_User_By_Id_Should_Return_Status_NoContent() {
		//given
		User user = new User();
		user.setId(1L);
		user.setEmail("user@email.com");
		user.setNickName("Nick");
		user.setPassword("123");
		
		ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
		
		//when
		ResponseEntity<String> deleted
				= restTemplate.exchange(restTemplate.getRootUri() + "/v1.0/users/1", HttpMethod.DELETE, null, String.class);
		
		//then
		Mockito.verify(userService, VerificationModeFactory.atLeastOnce()).deleteOne(idCaptor.capture());
		
		assertEquals(HttpStatus.NO_CONTENT, deleted.getStatusCode());
		assertEquals(1L, idCaptor.getValue());
	}
	
	@Test
	public void post_New_User_Should_Return_Status_Created_With_New_UserDto() {
		//given
		User user = new User();
		user.setEmail("user@email.com");
		user.setNickName("Nick");
		user.setPassword("123");
		
		UserDto userDto = modelMapper.map(user, UserDto.class);
		
		Mockito.when(userService.save(Mockito.any(User.class))).thenReturn(user);
		
		ArgumentCaptor<User> idCaptor = ArgumentCaptor.forClass(User.class);
		
		//when
		ResponseEntity<UserDto> responseEntity = restTemplate.postForEntity(
				restTemplate.getRootUri() + "/v1.0/users", userDto, UserDto.class);
		
		//then
		Mockito.verify(userService, VerificationModeFactory.atLeastOnce()).save(idCaptor.capture());
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(user.getEmail(), idCaptor.getValue().getEmail());
	}
	
	@Test
	public void put_Existing_User_By_Id_Should_Return_Status_Ok_With_UserDto() {
		//given
		User user = new User();
		user.setId(1L);
		user.setEmail("user@email.com");
		user.setNickName("Nick");
		user.setPassword("123");
		
		UserDto userDto = modelMapper.map(user, UserDto.class);
		
		Mockito.when(userService.update(Mockito.any(User.class))).thenReturn(user);
		
		ArgumentCaptor<User> idCaptor = ArgumentCaptor.forClass(User.class);
		
		//when
		ResponseEntity<UserDto> responseEntity = restTemplate.exchange(
				restTemplate.getRootUri() + "/v1.0/users", HttpMethod.PUT, new HttpEntity<>(userDto), UserDto.class);
		
		//then
		Mockito.verify(userService, VerificationModeFactory.atLeastOnce()).update(idCaptor.capture());
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(user.getEmail(), idCaptor.getValue().getEmail());
	}
	
}
