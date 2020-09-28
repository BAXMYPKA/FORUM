package ru.shop.forum.entities;

import lombok.Getter;
import lombok.Setter;
import ru.shop.entities.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "private_message", schema = "FORUM")
public class PrivateMessage extends AbstractForumEntity {

	@Transient
	protected static final long SerialVersionUID = 1L;
	
	@NotEmpty(message = "{field.notEmpty}")
	@Column
	private String title;
	
	@NotEmpty(message = "{field.notEmpty}")
	@Column(length = 1000)
	private String text;
	
//	@Column
//	private Boolean read;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "from_user_id", updatable = false)
	private User fromUser;
	
	@ManyToMany
	@JoinTable(name = "private_messages_to_user_id", schema = "FORUM",
			joinColumns = @JoinColumn(name = "private_message_id", updatable = false),
			inverseJoinColumns = @JoinColumn(name = "to_user_id"))
	private Set<User> toUsers;
}
