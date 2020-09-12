package ru.shop.forum.entities.utils;

import org.springframework.beans.factory.annotation.Autowired;
import ru.shop.forum.repositories.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class NickNameValidator implements ConstraintValidator<UniqueNickname, String> {
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public void initialize(UniqueNickname constraintAnnotation) {
	
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		return userRepository.existsUserByNickName(Objects.requireNonNullElse(value, ""));
	}
}
