package ru.shop.entities;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ru.shop.entities.utils.Sex;
import ru.shop.entities.utils.UniqueNickname;
import ru.shop.forum.entities.ForumUserSettings;
import ru.shop.forum.entities.ImgAvatar;
import ru.shop.forum.entities.Post;
import ru.shop.forum.entities.PrivateMessage;
import ru.shop.security.Roles;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "Users", schema = "SHOP")
public class User extends AbstractEntity {
	
	@Transient
	protected static final long SerialVersionUID = 1L;
	
	@NonNull
	@NotEmpty(message = "{field.notEmpty}")
	@Email(message = "{email.notValid}")
	@Column(unique = true, nullable = false)
	private String email;
	
	@Pattern(message = "{field.password}", regexp = "^[\\d\\w]{3,}$")
	@Column(nullable = false, length = 255)
	private String password;
	
	/**
	 * If null, the first part of a given email will be used, e.g. "user@mail.com" will be set as a "user" nickname.
	 */
	@UniqueNickname(message = "{user.nonUniqueNickname}")
//	@NonNull
//	@NotEmpty(message = "{field.notEmpty}")
	@Column(updatable = false, unique = true)
	private String nickName;
	
	@Size(min = 2, max = 35, message = "{user.nameLength}")
	@Column(length = 35)
	private String firstName;
	
	@Size(min = 2, max = 35, message = "{user.nameLength}")
	@Column
	private String lastName;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Past(message = "{date.past}")
	@Column
	private LocalDate birthdate;
	
	/**
	 * Accepts possible English variations like 'F', 'fm', 'fem', 'female' etc and Russian 'М', 'Ж', 'муж', 'жен' etc.
	 */
	@Pattern(flags = {Pattern.Flag.CASE_INSENSITIVE}, message = "{user.sexPattern}",
		regexp = "(?i)^(m|M|ma|MA|male|f|fe|fem|female|м|М|му|МУ|муж|мужской|Мужской|мужчина|ж|Ж|жен|женский|Женский)$")
	@Size(min = 1, max = 8, message = "{user.sex}")
	@Column(length = 8)
	//	@Convert(converter = SexSqlConverter.class) //No need if "autoApply=true" in the @Converter
	private Sex sex;
	
	@Size(max = 50, message = "{user.selfDescriptionLength}")
	@Column(name = "self_description", length = 50)
	private String selfDescription;
	
	@Column
	private boolean enabled = true;
	
	@Column
	private boolean locked = false;
	
	@Column(name = "locked_until")
	private LocalDate lockedUntil;
	
	/**
	 * Security role. Default is {@link Roles#USER}
	 */
	@Size(max = 35, message = "{user.RoleLength}")
	@Column(length = 35)
//	@Convert(converter = RolesConverter.class) //No need if "autoApply=true" in the @Converter
	private Roles role = Roles.USER;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "avatar_id")
	private ImgAvatar avatar;
	
	@OneToMany(mappedBy = "updatedBy")
	private Set<Post> postsUpdatedBy;
	
	@OneToOne(optional = false, orphanRemoval = true, mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private ForumUserSettings userForumSettings = new ForumUserSettings();
	
	@OneToMany(mappedBy = "fromUser")
	private Set<PrivateMessage> privateMessagesFrom;
	
	@ManyToMany(mappedBy = "toUsers")
	private Set<PrivateMessage> privateMessagesTo;
	
	@OneToOne
	private RegistrationConfirmationUuid uuid;
	
	//	private Address address;

//	private Set<Phone> phones;
	
	@PrePersist
	@Override
	public void prePersist() {
		super.prePersist();
		if (this.nickName == null || this.nickName.isBlank()) {
			this.nickName = this.email.substring(0, this.email.indexOf("@"));
		}
	}
}
