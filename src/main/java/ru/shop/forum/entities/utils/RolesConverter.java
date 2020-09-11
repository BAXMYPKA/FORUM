package ru.shop.forum.entities.utils;

import ru.shop.forum.security.Roles;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.NoSuchElementException;

@Converter(autoApply = true)
public class RolesConverter implements AttributeConverter<Roles, String> {
	
	/**
	 * @param roles
	 * @return A string representation of the given {@link Roles} or {@link Roles#ANONYMOUS#toString()} if the given parameter is null.
	 */
	@Override
	public String convertToDatabaseColumn(Roles roles) {
		if (roles == null) return Roles.ANONYMOUS.name();
		return roles.name();
	}
	
	/**
	 * @param s A possible case insensitive string representations of a {@link Roles}
	 * @return A {@link Roles} according to the given string or {@link Roles#ANONYMOUS} if nothing found.
	 */
	@Override
	public Roles convertToEntityAttribute(String s) {
		if (s == null || s.isBlank()) return Roles.ANONYMOUS;
		try {
			return Roles.getRoleByName(s);
		} catch (NoSuchElementException e) {
			return Roles.ANONYMOUS;
		}
	}
}
