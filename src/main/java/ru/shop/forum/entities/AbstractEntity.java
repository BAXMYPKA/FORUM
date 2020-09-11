package ru.shop.forum.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {
	
	@EqualsAndHashCode.Include
	@Id
	@Column(updatable = false, nullable = false)
	@GeneratedValue(generator = "IdGenerator", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "IdGenerator", sequenceName = "entity_id", schema = "FORUM", initialValue = 1000)
	private Long id;
	
	@Version
	private Long version;
	
	@EqualsAndHashCode.Include
	@PastOrPresent(message = "{date.pastOrPresent}")
	@Column
	private LocalDateTime created;
	
	@EqualsAndHashCode.Include
	@PastOrPresent(message = "{date.pastOrPresent}")
	@Column
	private LocalDateTime updated;
	
	@EqualsAndHashCode.Include
	@ManyToOne
	@JoinColumn(name = "created_by_user_id")
	private User createdBy;
	
	@EqualsAndHashCode.Include
	@ManyToOne
	@JoinColumn(name = "updated_by_user_id")
	private User updatedBy;
	
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
