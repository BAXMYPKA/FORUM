package ru.shop.entities.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import ru.shop.entities.User;
import ru.shop.services.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Returns true if a given Nickname is already occupied with another {@link User}.
 * Otherwise returns false.
 */
@NoArgsConstructor
@Setter
@Getter
public class NickNameValidator implements ConstraintValidator<UniqueNickname, String> {
	
	@Autowired
	private UserService userService;
	
	@Override
	public void initialize(UniqueNickname constraintAnnotation) {
	
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		if (value == null){
			return true;
		} else if (value.isBlank()) {
			return false;
		}
		return !userService.existsUserByNickName(value);
	}
}
