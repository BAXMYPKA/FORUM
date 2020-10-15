package ru.shop.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "uuids", schema = "SHOP")
public class RegistrationConfirmationUuid extends AbstractEntity{
	
	@Transient
	protected static final long SerialVersionUID = 1L;
	
	@NonNull
	@NotEmpty(message = "{field.notEmpty}")
	@Email(message = "{email.notValid}")
	@Column(unique = true, nullable = false)
	private UUID uuid;
	
	@OneToOne(orphanRemoval = true)
	@JoinColumn(name = "user_id", unique = true, nullable = false, updatable = false)
	private User user;
	
	/**
	 * Automatically creates a new UUID
	 */
	public RegistrationConfirmationUuid() {
		this.uuid = UUID.randomUUID();
	}
	
}
