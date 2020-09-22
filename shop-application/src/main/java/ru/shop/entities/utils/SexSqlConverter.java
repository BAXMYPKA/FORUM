package ru.shop.entities.utils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.NoSuchElementException;
import java.util.Objects;

@Converter(autoApply = true)
public class SexSqlConverter implements AttributeConverter<Sex, String> {
	
	/**
	 * @param sex A {@link Sex} enum for being turned into {@link String}
	 * @return A string representation of a given {@link Sex} enum. If null {@link Sex#MALE#toString()} will be returned
	 */
	@Override
	public String convertToDatabaseColumn(Sex sex) {
		return Objects.requireNonNullElse(sex, Sex.MALE).name();
	}
	
	/**
	 * @param s Case insensitive strings like "female" or "male"
	 * @return {@link Sex} enum according to a given string. In case of null or mismatch {@link Sex#MALE} will be returned
	 */
	@Override
	public Sex convertToEntityAttribute(String s) {
		try {
			return Sex.getSexByName(s);
		} catch (NoSuchElementException e) {
			//TODO: to logout
			return Sex.MALE;
		}
	}
}
