package ru.shop.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.shop.entities.User;
import ru.shop.entities.utils.Sex;
import ru.shop.repositories.UserRepository;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {BCryptPasswordEncoder.class, UserService.class, ModelMapper.class})
class UserServiceTest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@MockBean
	private UserRepository userRepositoryMock;
	
	private User user;
	
	@BeforeEach
	public void beforeEach() {
		user = new User("user@email.com");
		user.setPassword("123");
		user.setFirstName("FirstName");
		user.setSex(Sex.MALE);
		user.setBirthdate(LocalDate.now().minusYears(20));
		user.setNickName("Nick");
	}
	
	@AfterEach
	public void afterEach() {
		userService.deleteAll();
	}
	
	/**
	 * There are three fields separated by $:
	 * The “2a” represents the BCrypt algorithm version
	 * The “10” represents the strength of the algorithm
	 * The “ZLhnHxdpHETcxmtEStgpI.” part is actually the randomly generated salt. Basically, the first 22 characters are salt.
	 * The remaining part of the last field is the actual hashed version of the plain text
	 * Also, be aware that the BCrypt algorithm generates a String of length 60, so we need to make sure that the password will be stored in a column that can accommodate it.
	 */
	@Test
	public void new_User_Password_Should_Be_Encoded() {
		//given
		Mockito.when(userRepositoryMock.save(user)).thenReturn(user);
		//when
		User savedUser = userService.saveNewUnconfirmed(user);
		//then
		assertNotNull(savedUser.getPassword());
		assertNotEquals("123", savedUser.getPassword());
		assertTrue(savedUser.getPassword().startsWith("$2a$10$"));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"Password123", "1n2L3L4k5i6K", "1234567890ABCDEF"})
	public void encoded_User_Password_Should_Math_Non_Encoded_One(String nonEncodedPassword) {
		//given
		Mockito.when(userRepositoryMock.save(user)).thenReturn(user);
		user.setPassword(nonEncodedPassword);
		//when
		User savedUser = userService.saveNewUnconfirmed(user);
		//then
		assertTrue(savedUser.getPassword().startsWith("$2a$10$"));
		assertTrue(passwordEncoder.matches(nonEncodedPassword, savedUser.getPassword()));
	}
	
	@Test
	public void encoded_User_Password_Should_Not_Math_Wrong_Non_Encoded_One() {
		//given
		Mockito.when(userRepositoryMock.save(user)).thenReturn(user);
		//when
		User savedUser = userService.saveNewUnconfirmed(user);
		//then
		assertFalse(passwordEncoder.matches("122", savedUser.getPassword()));
	}
	
	@Test
	public void not_Found_UserByEmail_Should_Throw_NoResultException() {
		//given
		Mockito.when(userRepositoryMock.findByEmail("email@email.com")).thenReturn(Optional.empty());
		//when
		//then
		assertThrows(
			NoResultException.class,
			() -> userService.findUserByEmail("email@email.com"),
			"No User found for email email@email.com");
	}
}