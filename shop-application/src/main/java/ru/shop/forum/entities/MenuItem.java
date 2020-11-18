package ru.shop.forum.entities;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
public class MenuItem {
	
	@NotBlank(message = "{field.notEmpty}")
	private String name;
	
	private String description;
	
	@NotBlank(message = "{field.notEmpty}")
	private String link;
}
