package ru.shop.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.shop.entities.RegistrationConfirmationUuid;
import ru.shop.entities.User;
import ru.shop.entities.utils.Sex;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@DirtiesContext
class RegistrationConfirmationUuidServiceTest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RegistrationConfirmationUuidService uuidService;
	
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
	
	@Test
	public void new_User_Should_Be_Saved_With_New_RegistrationConfirmationUuid() {
		//given
		user.setRegistrationConfirmationUuid(new RegistrationConfirmationUuid(user));
		
		//when
		User savedUser = userService.save(user);
		
		//then
		RegistrationConfirmationUuid foundUuid =
			uuidService.findOne(savedUser.getRegistrationConfirmationUuid().getId()).get();
		
		assertEquals(savedUser.getId(), foundUuid.getUser().getId());
	}
	
	@Test
	public void User_Should_Be_Saved_With_New_RegistrationConfirmationUuid() {
		//given
		user.setRegistrationConfirmationUuid(new RegistrationConfirmationUuid(user));
		user = userService.save(user);
		
		String uuid = user.getRegistrationConfirmationUuid().getUuid();
		
		//when
		uuidService.confirmUuid(user.getRegistrationConfirmationUuid().getUuid());
		User confirmedUser = userService.findOne(user.getId()).get();
		
		//then
		assertNull(confirmedUser.getRegistrationConfirmationUuid());
		assertFalse(uuidService.findOneByUuid(uuid).isPresent());
	}
	
}