package ru.shop.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.NoSuchElementException;
import java.util.Objects;

public enum Roles implements GrantedAuthority {
	
	/**
	 * Anonymous, not registered user
	 */
	ANONYMOUS,
	/**
	 * Registered {@link ru.shop.entities.User}
	 */
	USER,
	/**
	 * Registered, but temporary locked {@link ru.shop.entities.User}
	 */
	LOCKED_USER,
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
