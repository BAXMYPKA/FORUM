package ru.shop.forum.entities.utils;

import java.util.*;

public enum Sex {
	
	MALE,
	FEMALE;
	
	private static Set<String> maleVariations = new HashSet<>();
	private static Set<String> femaleVariations = new HashSet<>();
	
	static {
		maleVariations.addAll(Arrays.asList("m", "male", "м", "муж", "мужской"));
		femaleVariations.addAll(Arrays.asList("f", "fm", "fem", "female", "ж", "жен", "женский"));
	}
	
	/**
	 * Accepts possible English variations like 'F', 'fm', 'fem', 'female' etc and Russian 'М', 'Ж', 'муж', 'жен' etc.
	 *
	 * @param sex Case insensitive sex
	 * @return {@link Sex}
	 * @throws NoSuchElementException If a given sex name mismatch any presented {@link Sex}
	 */
	public static Sex getSexByName(String sex) throws NoSuchElementException {
		Objects.requireNonNull(sex, "Sex cannot be null!");
		sex = sex.toLowerCase();
		if (maleVariations.contains(sex)) return Sex.MALE;
		else if (femaleVariations.contains(sex)) return Sex.FEMALE;
		else throw new NoSuchElementException("No sex enum found for a given " + sex);
	}
}
