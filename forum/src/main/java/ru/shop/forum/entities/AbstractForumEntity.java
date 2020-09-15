package ru.shop.forum.entities;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.shop.forum.entities.utils.ValidationCreateGroup;
import ru.shop.forum.entities.utils.ValidationUpdateGroup;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.time.LocalDateTime;

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
