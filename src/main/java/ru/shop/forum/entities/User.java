package ru.shop.forum.entities;

import lombok.*;
import ru.shop.forum.entities.utils.Sex;
import ru.shop.forum.security.Roles;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Users", schema = "FORUM")
public class User extends AbstractEntity {
	
	@Transient
	protected static final long SerialVersionUID = 1L;
	
	@NonNull
	@NotEmpty(message = "{field.notEmpty}")
	@Email(message = "{email.notValid}")
	@Column(unique = true, nullable = false)
	private String email;
	
	@Column
	private String password;
	
	@NonNull
	@Column(nullable = false)
	private String firstName;
	
	@Column
	private String lastName;
	
	@Past(message = "{date.past}")
	private LocalDate birthdate;
	
	@Column(length = 6)
	//	@Convert(converter = SexSqlConverter.class) //No need if "autoApply=true" in the @Converter
	private Sex sex;
	
	@Column
	@Lob
	@Size(max = 512000, message = "{photo.maxSize}")
	private byte[] photo;
	
	@Column(name = "self_description", length = 35)
	private String selfDescription;
	
	/**
	 * Security role. Default is {@link Roles#USER}
	 */
	@Column(length = 35)
//	@Convert(converter = RolesConverter.class) //No need if "autoApply=true" in the @Converter
	private Roles role = Roles.USER;
	
	@OneToMany(mappedBy = "updatedBy")
	private Set<Post> postsUpdatedBy;
	
	@OneToOne(optional = false, orphanRemoval = true, mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private UserForumSettings userForumSettings = new UserForumSettings();
	
	@OneToMany(mappedBy = "fromUser")
	private Set<PrivateMessage> privateMessagesFrom;
	
	@ManyToMany(mappedBy = "toUsers")
	private Set<PrivateMessage> privateMessagesTo;
}
