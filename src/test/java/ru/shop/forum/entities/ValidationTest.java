package ru.shop.forum.entities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

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
	public void validationMessagesShouldBeLocalized() {
		User user = new User();
		Set<ConstraintViolation<User>> constraintViolations = validator.validateProperty(user, "email");
		assertEquals(1, constraintViolations.size());
		assertEquals("Поле не должно быть пустым!", constraintViolations.iterator().next().getMessage());
	}
	
	@Test
	public void wrongValueShouldBeValidated() {
		Set<ConstraintViolation<User>> constraintViolations = validator.validateValue(User.class, "birthdate", LocalDate.now());
		assertEquals(1, constraintViolations.size());
		assertEquals("Дата должна быть в прошлом!", constraintViolations.iterator().next().getMessage());
	}
	
}