package ru.shop.forum.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.NoSuchElementException;
import java.util.Objects;

public enum Roles implements GrantedAuthority {

	ANONYMOUS,
	USER,
	BLOCKED_USER,
	MODERATOR,
	ADMIN;
	
	/**
	 * @param role Case insensitive role name
	 * @return {@link Roles}
	 * @throws NoSuchElementException If a given role name mismatch any presented {@link Roles}
	 */
	public static Roles getRoleByName(String role) throws NoSuchElementException {
		for (Roles roleValue : Roles.values()) {
			if (roleValue.name().equalsIgnoreCase(Objects.requireNonNull(role))) {
				return roleValue;
			}
		}
		throw new NoSuchElementException("The Role hasn't been found for name " + role);
	}
	
	@Override
	public String getAuthority() {
		return this.name();
	}
}
