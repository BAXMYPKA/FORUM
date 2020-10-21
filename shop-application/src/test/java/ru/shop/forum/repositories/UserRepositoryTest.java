package ru.shop.forum.repositories;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import ru.shop.entities.User;
import ru.shop.entities.utils.Sex;
import ru.shop.repositories.UserRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
//@TestPropertySource(properties = {"spring.jpa.properties.javax.persistence.validation.mode=none"})
class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;
	
	private static User user;
	
	@BeforeAll
	public static void beforeAll() {
		user = new User("user@email.com");
		user.setPassword("123");
		user.setFirstName("FirstName");
		user.setSex(Sex.MALE);
		user.setBirthdate(LocalDate.now().minusYears(20));
		user.setNickName("Nick");
	}
	
	@Test
	public void new_User_Should_Be_Persisted() {
		//given
		//when
		User savedUser = userRepository.saveAndFlush(user);
		//then
		assertNotNull(savedUser.getId());
		assertTrue(userRepository.existsById(savedUser.getId()));
	}
	
	@Test
	public void user_Should_Be_Found_By_Id() {
		//given
		User savedUser = userRepository.saveAndFlush(user);
		//when
		Optional<User> userById = userRepository.findById(savedUser.getId());
		//then
		assertAll(
			() -> assertFalse(userById.isEmpty()),
			() -> assertEquals("Nick", userById.get().getNickName()),
			() -> assertNotNull(userById.get().getId())
		);
	}
	
	@Test
	public void user_Should_Be_Found_By_Email() {
		//given
		User savedUser = userRepository.saveAndFlush(user);
		//when
		Optional<User> userByEmail = userRepository.findByEmail(savedUser.getEmail());
		//then
		assertAll(
			() -> assertFalse(userByEmail.isEmpty()),
			() -> assertEquals("user@email.com", userByEmail.get().getEmail()),
			() -> assertEquals("Nick", userByEmail.get().getNickName()),
			() -> assertNotNull(userByEmail.get().getId())
		);
	}
	
	@Test
	public void user_Should_Be_Found_By_NickName() {
		//given
		userRepository.saveAndFlush(user);
		//when
		Optional<User> userByNickName = userRepository.findByNickName("Nick");
		//then
		assertAll(
			() -> assertFalse(userByNickName.isEmpty()),
			() -> assertEquals("user@email.com", userByNickName.get().getEmail()),
			() -> assertEquals("Nick", userByNickName.get().getNickName()),
			() -> assertNotNull(userByNickName.get().getId())
		);
	}
	
	@Test
	public void duplicate_Email_Should_Be_Rejected() {
		//given
		User savedUser = userRepository.saveAndFlush(user);
		
		User duplicateEmailUser = new User("user@email.com");
		duplicateEmailUser.setPassword("1234");
		duplicateEmailUser.setFirstName("FirstName2");
		duplicateEmailUser.setSex(Sex.MALE);
		duplicateEmailUser.setBirthdate(LocalDate.now().minusYears(20));
		duplicateEmailUser.setNickName("Nick2");
		//when
		//then
		assertThrows(RuntimeException.class,
			() -> userRepository.saveAndFlush(duplicateEmailUser)
		);
	}
	
	@Test
	public void duplicate_NickName_Should_Be_Rejected() {
		//given
		User savedUser = userRepository.saveAndFlush(user);
		
		User duplicateNickNameUser = new User("user2@email.com");
		duplicateNickNameUser.setPassword("1234");
		duplicateNickNameUser.setFirstName("FirstName2");
		duplicateNickNameUser.setSex(Sex.MALE);
		duplicateNickNameUser.setBirthdate(LocalDate.now().minusYears(20));
		duplicateNickNameUser.setNickName("Nick");
		//when
		//then
		assertThrows(RuntimeException.class,
			() -> userRepository.saveAndFlush(duplicateNickNameUser)
		);
	}
	
	@Test
	public void preset_Id_Should_Be_Ignored() {
		//given
		User savedUser = userRepository.saveAndFlush(user);
		
		User presetIdUser = new User("user2@email.com");
		presetIdUser.setId(123L);
		presetIdUser.setPassword("1234");
		presetIdUser.setFirstName("FirstName2");
		presetIdUser.setSex(Sex.MALE);
		presetIdUser.setBirthdate(LocalDate.now().minusYears(20));
		presetIdUser.setNickName("Nick2");
		//when
		userRepository.saveAndFlush(presetIdUser);
		//then
		Optional<User> ignoredIdUser = userRepository.findByNickName("Nick2");
		assertNotEquals(123L, ignoredIdUser.get().getId());
	}
	
}