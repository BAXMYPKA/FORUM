package ru.shop.forum.entities;

import lombok.*;
import ru.shop.forum.entities.utils.Sex;
import ru.shop.forum.entities.utils.UniqueNickname;
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
	
	//TODO: to salt
	@Column(nullable = false)
	private String password;
	
	@UniqueNickname(message = "{user.nonUniqueNickname}")
	@NonNull
	@NotEmpty(message = "{field.notEmpty}")
	@Column(updatable = false, nullable = false, unique = true)
	private String nickName;
	
	@Size(min = 2, max = 35, message = "{user.nameLength}")
	@Column(length = 35)
	private String firstName;
	
	@Size(min = 2, max = 35, message = "{user.nameLength}")
	@Column
	private String lastName;
	
	@Past(message = "{date.past}")
	private LocalDate birthdate;
	
	/**
	 * Accepts possible English variations like 'F', 'fm', 'fem', 'female' etc and Russian 'М', 'Ж', 'муж', 'жен' etc.
	 */
	//TODO: to do a pattern
//	@Pattern()
	@Size(min = 1, max = 8, message = "{user.sex}")
	@Column(length = 8)
	//	@Convert(converter = SexSqlConverter.class) //No need if "autoApply=true" in the @Converter
	private Sex sex;
	
	@Column
	@Lob
	@Size(max = 512000, message = "{photo.maxSize}")
	private byte[] photo;
	
	@Size(max = 50, message = "{user.selfDescriptionLength}")
	@Column(name = "self_description", length = 50)
	private String selfDescription;
	
	/**
	 * Security role. Default is {@link Roles#USER}
	 */
	@Size(max = 35, message = "{user.RoleLength}")
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
