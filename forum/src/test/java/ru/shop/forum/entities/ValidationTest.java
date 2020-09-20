package ru.shop.forum.entities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ValidationTest {
	
	private Validator validator;
	
	@BeforeEach
	public void init() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
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
}