package ru.shop.entities;

import lombok.*;
import ru.shop.entities.utils.ValidationCreateGroup;
import ru.shop.entities.utils.ValidationUpdateGroup;

import javax.persistence.*;
import javax.validation.OverridesAttribute;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.UUID;

@NamedEntityGraphs(
	@NamedEntityGraph(name = "uuid-with-user", attributeNodes = {@NamedAttributeNode("user")})
)
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "uuids", schema = "SHOP")
public class RegistrationConfirmationUuid extends AbstractEntity {
	
	//TODO: to schedule database inspecting every 24h to clear outdated UUIDs
	
	@Transient
	protected static final long SerialVersionUID = 1L;
	
	@EqualsAndHashCode.Include
	private String uuid;
	
	@OneToOne
	@JoinColumn(name = "user_id", unique = true, nullable = false, updatable = false)
	private User user;
	
	/**
	 * Automatically creates a new UUID
	 */
	public RegistrationConfirmationUuid() {
		this.uuid = UUID.randomUUID().toString();
	}
	
	/**
	 * Automatically creates a new UUID and set this UUID to {@link User#setRegistrationConfirmationUuid(RegistrationConfirmationUuid)}
	 */
	public RegistrationConfirmationUuid(User user) {
		this.uuid = UUID.randomUUID().toString();
		this.user = user;
		this.user.setRegistrationConfirmationUuid(this);
	}
}
