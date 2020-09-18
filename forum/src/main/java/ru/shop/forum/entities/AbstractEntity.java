package ru.shop.forum.entities;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ru.shop.forum.entities.utils.ValidationCreateGroup;
import ru.shop.forum.entities.utils.ValidationUpdateGroup;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {
	
	private Class<? extends AbstractEntity> entityClass;
	
	@EqualsAndHashCode.Include
	@Null(groups = ValidationCreateGroup.class, message = "{field.mustBeNull}")
	@NotNull(groups = ValidationUpdateGroup.class, message = "{field.notEmpty}")
	@Id
	@Column(updatable = false, nullable = false)
	@GeneratedValue(generator = "idGenerator", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "idGenerator", sequenceName = "entity_id", schema = "SHOP", initialValue = 1000)
	private Long id;
	
	@Version
	private Long version;
	
	@EqualsAndHashCode.Include
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@PastOrPresent(message = "{date.pastOrPresent}")
	@Column
	private LocalDateTime created;
	
	@EqualsAndHashCode.Include
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@PastOrPresent(message = "{date.pastOrPresent}")
	@Column
	private LocalDateTime updated;
	
	@EqualsAndHashCode.Include
	@ManyToOne
	@JoinColumn(name = "created_by_user_id", updatable = false)
	private User createdBy;
	
	@EqualsAndHashCode.Include
	@ManyToOne
	@JoinColumn(name = "updated_by_user_id")
	private User updatedBy;
	
	public AbstractEntity() {
		this.entityClass = this.getClass();
	}
	
	@PrePersist
	public void prePersist() {
		this.created = LocalDateTime.now();
	}
	
	@PreUpdate
	public void preUpdate() {
		this.updated = LocalDateTime.now();
//		SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
}
