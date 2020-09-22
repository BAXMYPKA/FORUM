package ru.shop.forum.entities;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.shop.entities.AbstractEntity;

import javax.persistence.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
@MappedSuperclass
public abstract class AbstractForumEntity extends AbstractEntity {
	
//	@Version
//	private Long version;
	
//	@EqualsAndHashCode.Include
//	@Null(groups = ValidationCreateGroup.class, message = "{field.mustBeNull}")
//	@NotNull(groups = ValidationUpdateGroup.class, message = "{field.notEmpty}")
//	@Id
//	@Column(updatable = false, nullable = false)
//	@GeneratedValue(generator = "ForumIdGenerator", strategy = GenerationType.SEQUENCE)
//	@SequenceGenerator(name = "ForumIdGenerator", sequenceName = "entity_id", schema = "FORUM", initialValue = 1000)
//	private Long id;
	
}
