package ru.shop.forum.entities;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ru.shop.entities.User;
import ru.shop.entities.utils.Sex;
import ru.shop.entities.utils.UniqueNickname;
import ru.shop.security.Roles;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;


@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Users", schema = "FORUM")
public class ForumUser extends User {
	
	@Transient
	protected static final long SerialVersionUID = 1L;
	
	@UniqueNickname(message = "{user.nonUniqueNickname}")
	@NonNull
	@NotEmpty(message = "{field.notEmpty}")
	@Column(updatable = false, nullable = false, unique = true)
	private String nickName;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "avatar_id")
	private ImgAvatar avatar;
	
	@OneToMany(mappedBy = "updatedBy")
	private Set<Post> postsUpdatedBy;
	
	@OneToOne(optional = false, orphanRemoval = true, mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private UserForumSettings userForumSettings = new UserForumSettings();
	
	@OneToMany(mappedBy = "fromUser")
	private Set<PrivateMessage> privateMessagesFrom;
	
	@ManyToMany(mappedBy = "toUsers")
	private Set<PrivateMessage> privateMessagesTo;
	
}
