package ru.shop.forum.entities;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
@MappedSuperclass
public class AbstractEntity {
	
	private Class<? extends AbstractEntity> entityClass;
	
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
