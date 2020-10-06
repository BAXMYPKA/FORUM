package ru.shop.forum.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shop.entities.User;
import ru.shop.entities.utils.NickNameValidator;
import ru.shop.repositories.UserRepository;
import ru.shop.services.UserService;

import javax.validation.*;
import javax.validation.groups.Default;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EntitiesValidationTest {
	
	private Validator validator;
	
	private NickNameValidator nickNameValidator = new NickNameValidator();
	
	@Mock
	private UserService userService;
	
	@BeforeEach
	public void init() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
		this.nickNameValidator.setUserService(userService);
	}
	
	@Test
	public void validation_Messages_Should_Be_Localized() {
		User user = new User();
		Set<ConstraintViolation<User>> constraintViolations = validator.validateProperty(user, "email");
		assertEquals(1, constraintViolations.size());
		assertEquals("Поле не должно быть пустым!", constraintViolations.iterator().next().getMessage());
	}
	
	@Test
	public void wrong_Value_Should_Be_Validated() {
		Set<ConstraintViolation<User>> constraintViolations = validator.validateValue(User.class, "birthdate", LocalDate.now());
		assertEquals(1, constraintViolations.size());
		assertEquals("Дата должна быть в прошлом!", constraintViolations.iterator().next().getMessage());
	}
	
	@Test
	public void duplicate_NickName_Should_Be_Rejected() {
		//given
		Mockito.when(userService.existsUserByNickName("Nick")).thenReturn(true);
		//when
		boolean valid = nickNameValidator.isValid("Nick", null);
		//then
		assertFalse(valid);
		
	}
}