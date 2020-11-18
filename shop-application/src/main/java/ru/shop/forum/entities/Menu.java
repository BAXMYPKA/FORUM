package ru.shop.forum.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Menus", schema = "FORUM")
public class Menu extends AbstractForumEntity {
	
	@NotBlank(message = "{field.notEmpty}")
	private String name;
	
	private String description;
	
	@NotBlank(message = "{field.notEmpty}")
	private String link;
	
	@NotEmpty
	@ElementCollection
	@CollectionTable(name = "Menu_Items", schema = "FORUM")
	private Set<MenuItem> items;
}
