package ru.shop.forum.entities.utils;

import java.util.NoSuchElementException;
import java.util.Objects;

public enum Sex {
	
	MALE,
	FEMALE;
	
	/**
	 * @param sex Case insensitive sex
	 * @return {@link Sex}
	 * @throws NoSuchElementException If a given sex name mismatch any presented {@link Sex}
	 */
	public static Sex getSexByName(String sex) throws NoSuchElementException {
		for (Sex sexEnum : Sex.values()) {
			if (sexEnum.toString().equalsIgnoreCase(
					Objects.requireNonNull(sex, "Sex cannot be null!"))) return sexEnum;
		}
		throw new NoSuchElementException("No sex enum found for a given " + sex);
	}
}
